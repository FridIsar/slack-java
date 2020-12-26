package io.slack.controller;


import io.slack.front.Fenetre;
import io.slack.front.RightSidePanel;
import io.slack.front.ui.UIUser;
import io.slack.front.LeftSidePanel;
import io.slack.front.ToolBar;
import io.slack.model.Channel;
import io.slack.model.Post;
import io.slack.network.model.PostAndChannelCredentials;
import io.slack.network.model.UserAndChannelCredentials;
import io.slack.network.model.UserCredentials;
import io.slack.model.PostImage;
import io.slack.model.User;
import io.slack.network.Client;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.model.UserCredentialsOptions;
import io.slack.utils.EmailUtils;
import io.slack.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerClient {
    ////////////// management of users ///////////////////

    private static User currentUser =null;
    private static boolean connect = false;
    private static Client client=null;

    public static boolean isConnect(){return connect; }

    public static User getCurrentUser(){ return currentUser; }

    public static void login(String email, String password) {
        try {
            client = new Client();
            client.runClient();
            Message message = new MessageAttachment<UserCredentials>(ClientMessageType.SIGNIN.getValue(), new UserCredentials(email,password));
            Message received = client.sendMessage(message);

            //if( received ) TODO conditions on the received message
            connect=true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void createAcc(String pseudo, String email, String password ) {

        try {
            client = new Client();
            client.runClient();
            Message message = new MessageAttachment<UserCredentials>(ClientMessageType.SIGNUP.getValue(), new UserCredentialsOptions(email, password, pseudo));
            Message received = client.sendMessage(message);

            //if( received ) TODO conditions on the received message
            connect=true;
        } catch (IOException | InterruptedException e) {
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
        //TODO call the network to check if email is not used & if not, update the user instance (add a method in UserService)
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

    private static Channel currentChannel = null;
    private static ArrayList<Channel> channels = new ArrayList<>();

    public static Channel getCurrentChannel() {
        return currentChannel;
    }
    public static void setCurrentChannel(Channel currentChannel) {
        ControllerClient.currentChannel = currentChannel;
        ArrayList<User> userList = getUserListInChannel(currentChannel);
        RightSidePanel.getPanel().removeAllUsers();
        for(User user : userList){
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
            Channel channel=new Channel(title, currentUser);
            Message message = new MessageAttachment<Channel>(ClientMessageType.CREATECHANNEL.getValue(), channel);
            Message received;
            if(client!=null)
                received = client.sendMessage(message);

            channel.addPost( new Post(new User("root@slack.com", "root", "creator"), "Welcome to the '"+channel.getTitle()+"' channel") );


            return channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addUserInChannel(User user, Channel channel){
        UserAndChannelCredentials attachment = new UserAndChannelCredentials(user.getEmail(), channel.getTitle());
        Message message = new MessageAttachment<UserAndChannelCredentials>(ClientMessageType.ADDUSERCHANNEL.getValue(), attachment);
        try {
            client.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getUserListInChannel(Channel channel){

        try {
            Message message = new MessageAttachment<Channel>(ClientMessageType.GETUSERSCHANNEL.getValue(), channel);
            Message received = client.sendMessage(message);

            //TODO get the arrayList
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





///////////////// management of the messages ////////////////

    private static File attachedFile=null;
    private static boolean isFileAttached = false;


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

    public static void sendMessage(Channel channel, String textMessage ){
            try {
                client.runClient();
                Post post;
                if( isFileAttached ) {
                    post = new PostImage(currentUser, textMessage, FileUtils.getImage(attachedFile));
                    resetAttachedFile();
                }else {
                    post = new Post(currentUser, textMessage);
                }
                PostAndChannelCredentials attachment = new PostAndChannelCredentials(post, currentChannel.getTitle());
                Message message = new MessageAttachment<PostAndChannelCredentials>(ClientMessageType.ADDPOSTCHANNEL.getValue(), attachment);
                Message received = client.sendMessage(message);
                channel.addPost(post);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

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
            Channel channel = createChannel("channel"+i);
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
            example.addPost( new Post(currentUser, "test "+i ) );
        }
    }


    public static void main(String[] args){
        test();
        Fenetre.getFrame().setVisible(true);

    }

}
