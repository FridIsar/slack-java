package io.slack.controller;


import io.slack.front.Fenetre;
import io.slack.front.RightSidePanel;
import io.slack.front.ui.UIUser;
import io.slack.front.LeftSidePanel;
import io.slack.front.ToolBar;
import io.slack.model.Channel;
import io.slack.model.Post;
import io.slack.network.model.*;
import io.slack.model.User;
import io.slack.network.Client;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.EmailUtils;
import io.slack.utils.FileUtils;
import io.slack.utils.Utils;

import javax.print.DocFlavor;
//import javax.rmi.CORBA.Util;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerClient {
    //user
    private static User currentUser =null;
    private static boolean connect = false;
    private static Client client=null;

    //channel
    private static Channel currentChannel = null;
    private static ArrayList<Channel> channels = new ArrayList<>();

    //post
    private static File attachedFile=null;
    private static boolean isFileAttached = false;

    private static ControllerClient controllerClient = new ControllerClient();

    public ControllerClient(){
        try {
            client=new Client(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

////////////// management of users ///////////////////

    public static boolean isConnect(){return connect; }

    public static User getCurrentUser(){ return currentUser; }

    public static void login(String email, String password) {
        try {
            Message message = new MessageAttachment<UserCredentials>(ClientMessageType.SIGNIN.getValue(), new UserCredentials(email,password));
            Message received = client.sendMessage(message);

            if( received.hasAttachment() ){
                currentUser = (User)( ( (MessageAttachment)received).getAttachment() ) ;
                connect=true;

                getAllChannelUser(currentUser);
            }//todo affiche popup according to error code
        } catch ( InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void createAcc(String pseudo, String email, String password ) {

        try {
            Message message = new MessageAttachment<UserCredentials>(ClientMessageType.SIGNUP.getValue(), new UserCredentialsOptions(email, password, pseudo));
            Message received = client.sendMessage(message);

            if( received.hasAttachment() ){
                currentUser = (User)( ( (MessageAttachment)received).getAttachment() ) ;
                connect=true;
            }//todo affiche popup according to error code
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getAllChannelUser(User user){
        Message message = new MessageAttachment<User>(ClientMessageType.GETCHANNELSUSER.getValue(), user);
        try {
            Message received = client.sendMessage(message);

            if(received.hasAttachment() ){
                channels = (ArrayList<Channel>)( ( (MessageAttachment)received).getAttachment() );
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   public static void profil() {
        Fenetre.getFenetre().setContenu( new UIUser(currentUser) );
    }

    public static void disconnect() {

        Message message = new MessageAttachment<UserCredentials>(ClientMessageType.DISCONNECT.getValue(), new UserCredentials(currentUser.getEmail(), currentUser.getPassword()));
        try {
            client.sendMessage(message);
            currentUser =null;
            connect=false;
            client=null;

            currentChannel=null;
            channels=null;

            ToolBar.getToolBar().addMyButton();
            LeftSidePanel.getPanel().addMyButton();

            Fenetre.getFenetre().backToHome();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAccount() {
        Message message=new MessageAttachment<UserCredentials>(ClientMessageType.DELETEUSER.getValue(), new UserCredentials(currentUser.getEmail(), currentUser.getPassword()));
        try {
            client.sendMessage(message);
            disconnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void updateUser(User user, String email, String pseudo) {
        user.setEmail(email);
        user.setPseudo(pseudo);
        Message message = new MessageAttachment<UserCredentialsOptions>(ClientMessageType.UPDATEUSER.getValue(), new UserCredentialsOptions(email, user.getPassword(), pseudo ));

        try {
            client.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(User user, String oldPassword, String newPassword) {
        if(user.getPassword().equals(oldPassword)){
            if(EmailUtils.isPassword(newPassword)) {
                user.setPassword(newPassword);
                Message message = new MessageAttachment<UserCredentialsOptions>(ClientMessageType.UPDATEUSER.getValue(), new UserCredentialsOptions(user.getEmail(), newPassword, user.getPseudo()));
                try {
                    client.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                Fenetre.getFenetre().affichePopup(new String[] {"veuillez taper un mot de passe au bon format  [ minimum 8 caractère : 1 maj | 1 min | 1 chiffre | 1 caractère spécial ]"} );
            }
        }else{
            Fenetre.getFenetre().affichePopup( new String[]{"le mot de passe ne correspond pas"} );
        }
    }


//////////////// management of channels  ////////////////


    public static Channel getCurrentChannel() {
        return currentChannel;
    }
    public static void setCurrentChannel(Channel currentChannel) {
        ControllerClient.currentChannel = currentChannel;
        getUserListInChannel(currentChannel);
        getPostListInChannel(currentChannel);
        //ArrayList<User> userList = getUserListInChannel(currentChannel);
        RightSidePanel.getPanel().removeAllUsers();
        for(User user : currentChannel.getUsers()){
            RightSidePanel.getPanel().addAUser(user);
        }


    }
    public static void resetCurrentChannel(){
        ControllerClient.currentChannel=null;
        RightSidePanel.getPanel().removeAllUsers();
    }

    public static void addChannel(Channel chat) {
        channels.add(chat);
    }

    public static Channel createChannel(String title){
        try {
            Message message = new MessageAttachment<ChannelCredentials>(ClientMessageType.CREATECHANNEL.getValue(), new ChannelCredentials(title,currentUser.getEmail()));
            Message received;
            if(client!=null) {
                received = client.sendMessage(message);
                if(received.hasAttachment()){
                    Channel channel= (Channel)( ((MessageAttachment)received).getAttachment() );
                    sendPost(new User("root@slack.com", "root", "creator"), channel,"Welcome to the '"+channel.getTitle()+"' channel");
                    return channel;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeChannelIcon(Image image, Channel channel){

    }

    public static void addUserInChannel(String email, Channel channel){
        UserAndChannelCredentials attachment = new UserAndChannelCredentials(email, channel.getTitle());
        Message message = new MessageAttachment<UserAndChannelCredentials>(ClientMessageType.ADDUSERCHANNEL.getValue(), attachment);
        try {
            client.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void receiveAddUserInChannel(User user, Channel channel){
        for(Channel c : channels){
            if(c.equals(channel)){
                c.addUser(user);
            }
        }
    }

    public static void removeUserInChannel(String email, Channel channel){
        UserAndChannelCredentials attachment = new UserAndChannelCredentials(email, channel.getTitle());
        Message message = new MessageAttachment<UserAndChannelCredentials>(ClientMessageType.DELETEUSERCHANNEL.getValue(), attachment);
        try {
            client.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void receiveRemoveUserInChannel(User user, Channel channel){
        for(Channel c : channels){
            if(c.equals(channel)){
                c.removeUser(user);
            }
        }
    }

    public static User getUserInCurrentChannel(int i){
        return currentChannel.getUsers().get(i);
    }

    public static ArrayList<User> getUserListInChannel(Channel channel){

        try {
            Message message = new MessageAttachment<Channel>(ClientMessageType.GETUSERSCHANNEL.getValue(), channel);
            Message received = client.sendMessage(message);

            if(received.hasAttachment()){
                channel.setUsers( (ArrayList<User>)( ( (MessageAttachment)received).getAttachment() )  );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static ArrayList<User> getPostListInChannel(Channel channel){

        try {
            Message message = new MessageAttachment<Channel>(ClientMessageType.GETPOSTSCHANNEL.getValue(), channel);
            Message received = client.sendMessage(message);

            if(received.hasAttachment()){
                channel.setPosts( (ArrayList<Post>)( ( (MessageAttachment)received).getAttachment() )  );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static Channel getChannel(int i) {
        return channels.get(i);
    }

    public static void deleteChannel(){

    }





///////////////// management of the posts ////////////////

    public static void setAttachedFile(File file) {
        if(file!=null) {
            attachedFile = file;
            isFileAttached = true;
        }
    }

    private static void resetAttachedFile() {
        attachedFile = null;
        isFileAttached = false;
    }

    public static void sendPost(Channel channel, String textMessage ){
        sendPost(currentUser,channel,textMessage);
    }
    public static void sendPost(User user, Channel channel, String textMessage){
        try {
            PostAndChannelCredentials attachment;
            //Post post;
            if( isFileAttached ) {
                //post = new PostImage(currentUser, textMessage, FileUtils.getImage(attachedFile));
                attachment = new PostAndChannelCredentials(user.getEmail(),textMessage, FileUtils.getImage(attachedFile) , channel.getTitle());
                resetAttachedFile();
            }else {
                //post = new Post(currentUser, textMessage, currentChannel);
                attachment = new PostAndChannelCredentials(user.getEmail(),textMessage, null , channel.getTitle());
            }
            Message message = new MessageAttachment<PostAndChannelCredentials>(ClientMessageType.ADDPOSTCHANNEL.getValue(), attachment);
            Message received = client.sendMessage(message);
            //channel.addPost(post);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void receivePost(Post post){
        for(Channel channel : channels){
            if(post.getChannel().equals(channel)){
                channel.addPost(post);
                break;
            }
        }
    }

    public static void deletePost(){ //todo

    }

    public static void receiveDeletePost(Post post){
        for(Channel channel : channels){
            if(post.getChannel().equals(channel)){
                channel.removePost(post);
            }
        }
    }





//////////////////// server error //////////////

    public void receiveErrorServer(){
        int waitingTime = 15;
        Fenetre.getFenetre().affichePopup(new String[]{"the application will close in "+ waitingTime +" seconds..."}, "serveur error");
        Utils.wait(waitingTime);
        System.exit(1);
    }



///////////// main and test ////////////////

    //variable for the tests

    private static ArrayList<User> usersTest = new ArrayList<User>();

    public static User getUserTest(int i){
        return usersTest.get(i);
    }

    public static void test(){
        User nidhal = new User("nidhal@gmail.com","root","nidhal");
        connect=true;
        currentUser = nidhal;

        //left pannel
        for(int i=0; i<20; i++){
            //Channel channel = createChannel("channel"+i);
            Channel channel = new Channel("channel"+i, currentUser);
            channels.add(channel);
            LeftSidePanel.getPanel().addAChat(channel);
        }

        Channel example = new Channel( "test ", currentUser);
        LeftSidePanel.getPanel().addAChat(example);

        //right pannel
        for(int i=0; i<20; i++){
            User user = new User("root_"+i+"@slack.com","root","root_"+i);
            usersTest.add(user);
            RightSidePanel.getPanel().addAUser(user);
        }

        for(int i=0; i<50; i++){
            example.addPost( new Post(currentUser, "test "+i, currentChannel ) );
        }
    }


    public static void main(String[] args){
        //test();
        Fenetre.getFrame().setVisible(true);

    }

}
