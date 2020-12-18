package io.slack.controller;

import io.slack.dao.DatabaseUser;
import io.slack.front.Chat;
import io.slack.front.Fenetre;
import io.slack.front.Message;
import io.slack.front.PageUser;
import io.slack.front.PanneauLateralGauche;
import io.slack.front.ToolBar;
import io.slack.model.User;
import io.slack.utils.EmailUtils;

import java.util.ArrayList;

public class ControllerClient {
    private static User user=null;
    private static boolean connect = false;


    private static ArrayList<Chat> listeChat = new ArrayList<Chat>();

    public static void addChat( Chat c ){
        listeChat.add(c);
    }
    public static Chat getChat(int i){
        return listeChat.get(i);
    }


    public static User getUser() {return user; }
    public static boolean isConnect(){
        return connect;
    }

    // TODO :
    //      => creation of a methode on Client side to generarte (set) a specifique TYpe of Message
    //      methode to call with following parametters  : ClientMessageType.SIGNIN.getValue() ET Object Credentials
    public static boolean login(String email, String password) {

	    if(DatabaseUser.getDatabase().existEmail(email)) {
	    	User u= DatabaseUser.getDatabase().connexion(email,password);
	    	if(u!=null){
                connect = true;
                ToolBar.getToolBar().addMyButton();
                PanneauLateralGauche.getPanneau().addMyButton();
                return true;
	    	}else{
	    		Fenetre.getFenetre().affichePopup(new String[]{"mot de passe ne correspond pas"});
	    		return false;
	    	}
	    }else{
	    	Fenetre.getFenetre().affichePopup(new String[]{"email non existant"});
	    	return false;
	    }
    }




    public static boolean signin(String pseudo, String email, String password ) {

	    boolean[] check= checkUserData(email, password);
	    if(check[0] && check[1]) {
            user= new User(email, password, pseudo);
            connect = true;
            ToolBar.getToolBar().addMyButton();
            PanneauLateralGauche.getPanneau().addMyButton();
            return true;

	    }else
	    	return  false;


    }

    public static boolean[] checkUserData(String email, String mdp){
        boolean[] res = {true, true};
        String[] message = new String[3];
        //email
        if(! EmailUtils.isEmail(email) ){
            res[0]=false;
            for(int i=0; i<message.length; i++){
                if(message[i]==null) message[i]="veuillez retaper un email au bon format [ exemple aaa@lip6.fr ]";
            }
        }
        if(DatabaseUser.getDatabase().existEmail(email)) {
            res[0]=false;
            for(int i=0; i<message.length; i++){
                if(message[i]==null) message[i]="email déjà utilisé";
            }
        }

        //password
        if(! EmailUtils.isPassword(mdp)){

            res[1]=false;
            for(int i=0; i<message.length; i++){
                if(message[i]==null) message[i]="veuillez taper un mot de passe au bon format  [ minimum 8 caractère : 1 maj | 1 min | 1 chiffre | 1 caractère spécial ]";
            }
        }

        if( ! res[0] || ! res[1] )
            Fenetre.getFenetre().affichePopup(message);

        return res;
    }






   public static void profil() {
        PageUser.afficheProfil(user);
    }

    public static void disconnect() {
        user=null;
        connect=false;
        ToolBar.getToolBar().addMyButton();
        PanneauLateralGauche.getPanneau().addMyButton();

        Fenetre.getFenetre().backToHome();
    }

    public static void deleteAccount() {
        //DatabaseUser.getDatabase().deleteUser(user.getId());
	    disconnect();
    }

    public static void test(){
        /*for(int i=0; i<20; i++){
            io.slack.front.PanneauLateralGauche.getPanneau().addAbutton( io.slack.utils.Imagerie.getImage( "Icons/logo.png" ) );
        }*/

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

    }


}
