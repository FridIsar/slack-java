package io.slack.controller;


import io.slack.front.Fenetre;
import io.slack.front.RightSidePanel;
import io.slack.front.ui.UIUser;
import io.slack.front.LeftSidePanel;
import io.slack.front.ToolBar;
import io.slack.model.Channel;
import io.slack.model.Credentials;
import io.slack.model.MessageImage;
import io.slack.model.User;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.EmailUtils;
import io.slack.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControllerClient {
    private static User userCourant =null;
    private static boolean connect = false;
    private static ArrayList<Channel> channels = new ArrayList<>();

    private static File attachedFile=null;
    private static boolean isFileAttached = false;


    public static boolean isConnect(){return connect; }


    ////////////// management of users ///////////////////

    public static User getUserCourant(){ return userCourant; }

    //TODO to do
    public static boolean login(String email, String password) {
        //appel au réseau

        //TODO call the network
        Message message = new MessageAttachment<Credentials>(ClientMessageType.SIGNIN.getValue(), new Credentials());
        // le credential à adapter



        connect=true;
        return true;
    }



    //TODO to do
    public static boolean createAcc(String pseudo, String email, String password ) {
        //appel au réseau

        Message message = new MessageAttachment<Credentials>(ClientMessageType.SIGNIN.getValue(), new Credentials());
        //credential à adapter


        connect=true;
        return true;

    }

   public static void profil() {
        Fenetre.getFenetre().setContenu( new UIUser(userCourant) );
    }

    //TODO call the network to retire the user from the connected users list
    public static void disconnect() {
        userCourant =null;
        connect=false;

        ToolBar.getToolBar().addMyButton();
        LeftSidePanel.getPanel().addMyButton();

        Fenetre.getFenetre().backToHome();
    }

    //TODO call the network pour access DAOFactory.getUser().delete( user.getEmail() )
    public static void deleteAccount() {
        //DatabaseUser.getDatabase().deleteUser(user.getId());
	    disconnect();
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
                //TODO call the network to update the user instance
            }else{
                Fenetre.getFenetre().affichePopup(new String[] {"veuillez taper un mot de passe au bon format  [ minimum 8 caractère : 1 maj | 1 min | 1 chiffre | 1 caractère spécial ]"} );
            }
        }else{
            Fenetre.getFenetre().affichePopup( new String[]{"le mot de passe ne correspond pas"} );
        }
    }

    /////// management of channels  ////////////////


    //TODO to do
    public static void addChat(Channel chat) {
        //TODO call the network to register user in the channel

        channels.add(chat);
    }

    public static Channel createChannel(String nom){
        Channel channel=new Channel(nom, userCourant);
        channel.addMessage( new io.slack.model.Message(new User("root@slack.com", "root", "createur"), "Welcome to the '"+channel.getName()+"' channel") );

        //TODO call the network

        return channel;
    }

    public static Channel getChannel(int i) {
        return channels.get(i);
    }

    ////////// management of the messages ////////////////


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
        if( isFileAttached ){
            //TODO call the network to add a message in a channel
            channel.addMessage( new MessageImage(userCourant, textMessage , FileUtils.getImage(attachedFile ) ));
            resetAttachedFile();
        }else
            channel.addMessage(new io.slack.model.Message(userCourant, textMessage) );

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
        userCourant = nidhal;

        //left pannel
        for(int i=0; i<20; i++){
            Channel channel = createChannel("channel"+i);
            LeftSidePanel.getPanel().addAChat(channel);
        }

        Channel example = new Channel( "test ", userCourant);
        LeftSidePanel.getPanel().addAChat(example);

        //right pannel
        for(int i=0; i<20; i++){
            User user = new User("root_"+i+"@slack.com","root","root_"+i);
            usersTest.add(user);
            RightSidePanel.getPanel().addAUser(user);
        }

        for(int i=0; i<50; i++){
            example.addMessage( new io.slack.model.Message(userCourant, "test "+i ) );
        }
    }


    public static void main(String[] args){
        test();
        Fenetre.getFrame().setVisible(true);

    }

}
