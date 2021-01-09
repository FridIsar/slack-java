package io.slack.controller;


import io.slack.front.Window;
import io.slack.front.RightSidePanel;
import io.slack.front.ui.UIUser;
import io.slack.front.LeftSidePanel;
import io.slack.front.ToolBar;
import io.slack.model.*;
import io.slack.network.model.*;
import io.slack.network.Client;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.EmailUtils;
import io.slack.utils.FileUtils;
import io.slack.utils.Utils;

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

    //friend
    private static ArrayList<Friend> friends = new ArrayList<>();
    private static ArrayList<User> userFriends = new ArrayList<>();

    //post
    private static File attachedFile=null;
    private static boolean isFileAttached = false;

    private static ControllerClient controllerClient = new ControllerClient();

    public ControllerClient(){
    }

////////////// management of users ///////////////////

    public static boolean isConnect(){return connect; }

    public static User getCurrentUser(){ return currentUser; }

    public static void login(String email, String password) {
        try {
            client = new Client(controllerClient);
            Message message = new MessageAttachment<UserCredentials>(ClientMessageType.SIGNIN.getValue(), new UserCredentials(email,password));
            Message received = client.sendMessage(message);

            if( received.hasAttachment() ){
                currentUser = (User)( ( (MessageAttachment)received).getAttachment() ) ;
                connect=true;

                ToolBar.getToolBar().addMyButton();
                LeftSidePanel.getPanel().addMyButton();
                getAllChannelUser(currentUser);
                getAllFriendUser(currentUser);
            }//todo affiche popup according to error code
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }



    public static void createAcc(String pseudo, String email, String password ) {

        try {
            client = new Client(controllerClient);
            Message message = new MessageAttachment<UserCredentials>(ClientMessageType.SIGNUP.getValue(), new UserCredentialsOptions(email, password, pseudo));
            Message received = client.sendMessage(message);

            if( received.hasAttachment() ){
                currentUser = (User)( ( (MessageAttachment)received).getAttachment() ) ;
                connect=true;
                ToolBar.getToolBar().addMyButton();
                LeftSidePanel.getPanel().addMyButton();
            }//todo affiche popup according to error code
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAllChannelUser(User user){
        Message message = new MessageAttachment<User>(ClientMessageType.GETCHANNELSUSER.getValue(), user);
        try {
            Message received = client.sendMessage(message);
            if(received.hasAttachment() ){
                channels = (ArrayList<Channel>)( ( (MessageAttachment)received).getAttachment() );
                for(Channel channel : channels){
                    LeftSidePanel.getPanel().addAChat(channel);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getAllFriendUser(User user){
        Message message = new MessageAttachment<UserCredentials>(ClientMessageType.GETFRIENDS.getValue(), new UserCredentials(user.getEmail(), user.getPassword()));
        try {
            Message received = client.sendMessage(message);
            if(received.hasAttachment() ){
                friends = (ArrayList<Friend>)( (MessageAttachment)received).getAttachment();
                for(Friend friend : friends){
                    if(currentUser.equals( friend.getFirstUser() ) )
                        userFriends.add(friend.getSecUser());
                    else
                        userFriends.add(friend.getFirstUser());
                }
                ToolBar.getToolBar().setFriendList(userFriends);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   public static void profil() {
        Window.getWindow().setContenu( new UIUser(currentUser) );
    }

    public static void disconnect(boolean send) {

        Message message = new MessageAttachment<UserCredentials>(ClientMessageType.DISCONNECT.getValue(), new UserCredentials(currentUser.getEmail(), currentUser.getPassword()));
        try {
            if(send)
                client.sendMessage(message);
            currentUser =null;
            connect=false;
            client=null;

            currentChannel=null;
            channels.clear();

            friends.clear();
            userFriends.clear();
            ToolBar.getToolBar().resetFriendList();
            ToolBar.getToolBar().addMyButton();
            LeftSidePanel.getPanel().addMyButton();
            LeftSidePanel.getPanel().resetList();

            Window.getWindow().backToHome();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAccount() {
        Message message=new MessageAttachment<UserCredentials>(ClientMessageType.DELETEUSER.getValue(), new UserCredentials(currentUser.getEmail(), currentUser.getPassword()));
        try {
            client.sendMessage(message);

            disconnect(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void updateUser(User user, String email, String pseudo) {
        UserCredentialsOptions uco = new UserCredentialsOptions(email, user.getPassword(), pseudo );
        uco.setId(user.getId());
        Message message = new MessageAttachment<UserCredentialsOptions>(ClientMessageType.UPDATEUSER.getValue(), uco);

        try {
            Message messageReceived = client.sendMessage(message);
            if (messageReceived.hasAttachment())     {
                User user1 = (User) ((MessageAttachment) messageReceived).getAttachment();
                currentUser.setPseudo(user1.getPseudo());
                currentUser.setEmail(user1.getEmail());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void updatePassword(User user, String oldPassword, String newPassword) {
        if(user.getPassword().equals(oldPassword)){
            if(EmailUtils.isPassword(newPassword)) {
                UserCredentialsOptions uco = new UserCredentialsOptions(user.getEmail(), newPassword, user.getPseudo());
                uco.setId(user.getId());
                Message message = new MessageAttachment<UserCredentialsOptions>(ClientMessageType.UPDATEUSER.getValue(), uco);
                try {
                    Message messageReceived = client.sendMessage(message);
                    if (messageReceived.hasAttachment())     {
                        currentUser.setPassword(newPassword);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                Window.getWindow().affichePopup(new String[] {"veuillez taper un mot de passe au bon format  [ minimum 8 caractère : 1 maj | 1 min | 1 chiffre | 1 caractère spécial ]"} );
            }
        }else{
            Window.getWindow().affichePopup( new String[]{"le mot de passe ne correspond pas"} );
        }
    }


//////////////// management of channels  ////////////////


    public static Channel getCurrentChannel() {
        return currentChannel;
    }
    public static void setCurrentChannel(Channel currentChannel) {
        ControllerClient.currentChannel = currentChannel;
        if(ControllerClient.currentChannel instanceof ChannelDirect){
            Friend friend= ((ChannelDirect) ControllerClient.currentChannel).getFriend();
            getPostListInFriend(friend);
            RightSidePanel.getPanel().removeAllUsers();
            RightSidePanel.getPanel().addAUser( friend.getFirstUser() );
            RightSidePanel.getPanel().addAUser( friend.getSecUser() );
        }else{
            LeftSidePanel.getPanel().removeNotif(channels.indexOf(currentChannel));
            getUserListInChannel(currentChannel);
            getPostListInChannel(currentChannel);
            RightSidePanel.getPanel().removeAllUsers();
            for (User user : currentChannel.getUsers()) {
                RightSidePanel.getPanel().addAUser(user);
            }
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
                    addChannel(channel);
                    LeftSidePanel.getPanel().addAChat(channel);
                    LeftSidePanel.getPanel().refreshList(channels);
                    System.out.println("channel is "+channel);
                    sendPost(channel.getAdmin(), channel,"Welcome to the '"+channel.getTitle()+"' channel");
                    return channel;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void receiveCreateChannel(Channel channel)   {
        LeftSidePanel.getPanel().addAChat(channel);
        LeftSidePanel.getPanel().refreshList(channels);
    }

    public static void changeChannelIcon(Image image, Channel channel){

    }

    public static void addUserInChannel(String email, Channel channel){
        UserAndChannelCredentials attachment = new UserAndChannelCredentials(email, channel.getTitle());
        Message message = new MessageAttachment<UserAndChannelCredentials>(ClientMessageType.ADDUSERCHANNEL.getValue(), attachment);
        //System.out.println("attachment adduserinchannel "+((MessageAttachment) message).getAttachment());
        try {
            Message receivedMessage = client.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void receiveAddUserInChannel(User user, Channel channel){
        for(Channel c : channels){
            if(c.equals(channel)){
                c.addUser(user);
                RightSidePanel.getPanel().addAUser(user);
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
                ArrayList<User> users = (ArrayList<User>) (channel.getUsers());
                users.add(channel.getAdmin());
                RightSidePanel.getPanel().refreshList(users);
            }
        }
    }

    public static User getUserInCurrentChannel(int i){
        return currentChannel.getUsers().get(i);
    }

    public static void getUserListInChannel(Channel channel){

        try {
            Message message = new MessageAttachment<Channel>(ClientMessageType.GETUSERSCHANNEL.getValue(), channel);
            Message received = client.sendMessage(message);

            if(received.hasAttachment()){
                channel.setUsers( (ArrayList<User>)( ( (MessageAttachment)received).getAttachment() )  );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getPostListInChannel(Channel channel){
        try {
            Message message = new MessageAttachment<Channel>(ClientMessageType.GETPOSTSCHANNEL.getValue(), channel);
            Message received = client.sendMessage(message);
            if(received.hasAttachment()){
                channel.setPosts( (ArrayList<Post>)( ( (MessageAttachment)received).getAttachment() )  );
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Channel getChannel(int i) {
        return channels.get(i);
    }

    public static void deleteChannel(){
        try {
            Message message = new MessageAttachment<Channel>(ClientMessageType.DELETECHANNEL.getValue(), currentChannel);
            Message received;
            if(client!=null) {
                received = client.sendMessage(message);
                /*if (received.getCode() == 200)  {
                    channels.remove(currentChannel);
                    LeftSidePanel.getPanel().refreshList(channels);
                    Window.getFenetre().backToHome();
                    resetCurrentChannel();
                }*/
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void receiveDeleteChannel(Channel channel){
        Channel channel1 = null;
        for (Channel chan : channels)   {
            if (chan.equals(channel))   {
                channel1 = chan;
            }
        }
        if (channel1 != null)
            channels.remove(channel1);
        LeftSidePanel.getPanel().refreshList(channels);
        if (currentChannel.equals(channel))    {
            Window.getWindow().backToHome();
            resetCurrentChannel();
        }
    }

///////////////// management of friends /////////////////////////


    public static boolean isAFriend(User user){
        boolean isFriend = userFriends.contains(user);
        return isFriend;
    }

    public static void addAFriend(User user){
        Message message = new MessageAttachment<UserAndUserCredentials>(ClientMessageType.ADDFRIEND.getValue(), new UserAndUserCredentials(currentUser.getEmail(), user.getEmail()));
        try {
            Message received = client.sendMessage(message);
            if( received.hasAttachment() ){
                Friend friend = (Friend)((MessageAttachment)received).getAttachment();
                friends.add(friend);
                if(currentUser.equals( friend.getFirstUser() ) )
                    userFriends.add(friend.getSecUser());
                else
                    userFriends.add(friend.getFirstUser());
                ToolBar.getToolBar().addAFriend(friend.getSecUser());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAFriend(User user){
        Message message = new MessageAttachment<UserAndUserCredentials>(ClientMessageType.DELETEFRIEND.getValue(), new UserAndUserCredentials(currentUser.getEmail(), user.getEmail()));
        try {
            Message received = client.sendMessage(message);
            if( received.hasAttachment() ){
                Friend friend = (Friend)((MessageAttachment)received).getAttachment();

                Friend aFriend;
                User aUser;
                for (int i = 0; i < friends.size(); i++)  {
                    aFriend = friends.get(i);
                    aUser = userFriends.get(i);
                    if (aFriend.getFirstUser().equals(user) || aFriend.getSecUser().equals(user)) {
                        friends.remove(aFriend);
                        userFriends.remove(aUser);
                        ToolBar.getToolBar().removeAFriend(i);
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendPostFriend(Friend friend, String textMessage){
        User user;
        if(currentUser.equals(friend.getFirstUser()))
            user=friend.getSecUser();
        else
            user=friend.getFirstUser();

        try {
            PostAndFriendCredentials attachment;
            //Post post;
            if( isFileAttached ) {
                attachment = new PostAndFriendCredentials(currentUser.getEmail(),textMessage, FileUtils.getImage(attachedFile) , user.getEmail() );
                resetAttachedFile();
            }else {
                attachment = new PostAndFriendCredentials(currentUser.getEmail(),textMessage, null , user.getEmail());
            }
            Message message = new MessageAttachment<PostAndFriendCredentials>(ClientMessageType.ADDPOSTFRIEND.getValue(), attachment);
            Message received = client.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getPostListInFriend(Friend friend){
        try {
            Message message = new MessageAttachment<UserAndUserCredentials>(ClientMessageType.GETPOSTSFRIEND.getValue(), new UserAndUserCredentials(friend.getFirstUser().getEmail(), friend.getSecUser().getEmail()) );
            Message received = client.sendMessage(message);
            if(received.hasAttachment()){
                friend.getChannelDirect().setPosts((ArrayList<Post>)((MessageAttachment)received ).getAttachment() );
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ChannelDirect getChannelDirectFriend(User user){
        for(Friend friend : friends){
            if( (friend.getFirstUser().equals(currentUser) && friend.getSecUser().equals(user) )  ||  ( (friend.getFirstUser().equals(user) && friend.getSecUser().equals(currentUser) )  )  ){
                return friend.getChannelDirect();
            }
        }
        return null;
    }

    public void receivePostFriend(PostDirect postDirect){ //todo call this method after notifying
        for(Friend friend : friends){
            if(friend.equals(postDirect.getFriend())){
                friend.getChannelDirect().addPost(postDirect);
                if(currentChannel.equals( friend.getChannelDirect() ))
                    Window.getWindow().refreshPage();
                break;
            }
        }
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
                if(! channel.equals(currentChannel))    {
                    //TODO LeftSidePanel.getPanel().addNotif(channels.indexOf(channel));
                    System.out.println("todo notif");
                }
                else    {
                    Window.getWindow().refreshPage();
                }
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
        int waitingTime = 5;
        Window.getWindow().affichePopup(new String[]{"the application will close in "+ waitingTime +" seconds..."}, "serveur error");
        Utils.wait(waitingTime);
        System.exit(1);
    }



///////////// main and test ////////////////

    //variable for the tests

    private static ArrayList<User> usersTest = new ArrayList<User>();

    public static User getUserTest(int i){
        return usersTest.get(i);
    }

    public static void test() {
        System.out.println("starting test");
        try {
            client = new Client(controllerClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        login("isar@gmail.com", "@Glitch1");
        getAllChannelUser(currentUser);
        Channel channel = createChannel("titre"+Math.random()*500);
        addUserInChannel("nidhal@gmail.com", channel);




        /*//left pannel
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
        }*/
    }


    public static void main(String[] args){
        //test();
        Window.getFrame().setVisible(true);
    }

}
