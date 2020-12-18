package io.slack.controller;


import io.slack.front.Fenetre;
import io.slack.front.PageUser;
import io.slack.front.PanneauLateralGauche;
import io.slack.front.ToolBar;
import io.slack.model.Channel;
import io.slack.model.Credentials;
import io.slack.model.User;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;


public class Systeme {

    /*TODO à supprimer, trouver un autre moyen pour savoir si un utilisateur est connecté sur l'interface */
    private static boolean connect=false;
    public static boolean isConnect(){return connect; }

    /*TODO à supprimer, trouver autre moyen pour récupérer le user courant */
    public static User getUserCourant(){ return new User("root@slack.com", "root", "createur"); }

    //TODO à faire
    public static boolean login(String email, String password) {
        //appel au réseau

        //TODO question : methode du réseau
        Message message = new MessageAttachment<Credentials>(ClientMessageType.SIGNIN.getValue(), new Credentials());
        // le credential à adapter



        connect=true;
        return true;
    }



    //TODO à faire
    public static boolean createAcc(String pseudo, String email, String password ) {
        //appel au réseau

        Message message = new MessageAttachment<Credentials>(ClientMessageType.SIGNIN.getValue(), new Credentials());
        //credential à adapter


        connect=true;
        return true;

    }

    //TODO user
   public static void profil() {
        PageUser.afficheProfil( new User("root@slack.com", "root", "createur") );
    }

    //TODO ajouter appel au réseau pour retirer le user de la liste des connectés
    public static void disconnect() {
        connect=false;

        ToolBar.getToolBar().addMyButton();
        PanneauLateralGauche.getPanneau().addMyButton();

        Fenetre.getFenetre().backToHome();
    }

    //TODO appelle au réseau pour accéder à DAOFactory.getUser().delete( user.getEmail() )
    public static void deleteAccount() {
        //DatabaseUser.getDatabase().deleteUser(user.getId());
	    disconnect();
    }

    //TODO à faire
    public static void addChat(Channel chat) {
    }

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
            example.addContenu( new Message(Systeme.getUser(), "test"+i ) );
        }
    }


    public static void main(String[] args){
        test();
        Fenetre.getFrame().setVisible(true);

    }*/

}
