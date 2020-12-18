package io.slack.controller;


import io.slack.front.Fenetre;
import io.slack.front.ui.UIUser;
import io.slack.front.LeftSidePanel;
import io.slack.front.ToolBar;
import io.slack.model.Channel;
import io.slack.model.Credentials;
import io.slack.model.User;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.util.ArrayList;

public class ControllerClient {
    private static User user=null;
    private static boolean connect = false;
    private static ArrayList<Channel> channels = new ArrayList<>();


    public static boolean isConnect(){return connect; }


    ////////////// management of users ///////////////////

    /*TODO à supprimer, trouver autre moyen pour récupérer le user courant */
    public static User getUserCourant(){ return new User("root@slack.com", "root", "createur"); }

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
        UIUser.afficheProfil( user );
    }

    //TODO call the network to retire the user from the connected users list
    public static void disconnect() {
        connect=false;

        ToolBar.getToolBar().addMyButton();
        LeftSidePanel.getPanneau().addMyButton();

        Fenetre.getFenetre().backToHome();
    }

    //TODO call the network pour access DAOFactory.getUser().delete( user.getEmail() )
    public static void deleteAccount() {
        //DatabaseUser.getDatabase().deleteUser(user.getId());
	    disconnect();
    }


    public static void updateUser(User contenu) {
        //TODO call the network
    }

    public static void updatePassword(String oldPassword, String newPassword) {
        //TODO call the network & update the user instance
    }

    /////// management of channels  ////////////////


    //TODO à faire
    public static void addChat(Channel chat) {
        //TODO call the network to register user in the channel

        channels.add(chat);

    }

    public static Channel createChannel(){
        Channel channel=null;

        //TODO call the network

        return channel;
    }







    ///////////// main and test ////////////////

    /*public static void test(){
        /*for(int i=0; i<20; i++){
            io.slack.front.PanneauLateralGauche.getPanneau().addAbutton( io.slack.utils.Imagerie.getImage( "Icons/logo.png" ) );
        }

        User nidhal = new User("nidhal@gmail.com","root","nidhal");
        connect=true;
        user = nidhal;

        Chat example = new Chat( "test ");
        PanneauLateralGauche.getPanneau().addAChat(example);



        for(int i=0; i<50; i++){
            example.addContenu( new Message(ControllerClient.getUser(), "test"+i ) );
        }
    }


    public static void main(String[] args){
        test();
        Fenetre.getFrame().setVisible(true);

    }*/

}
