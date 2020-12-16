package io.slack.model;

import io.slack.dao.DatabaseUser;
import io.slack.front.Fenetre;
import io.slack.front.PageUser;
import io.slack.utils.Outils;

import java.awt.Image;
import java.io.File;
import java.util.Date;

public class User {
    private String id;

    private String email;
    private String pseudo;
    private Image pdp;

    private File fichierJoint=null;
    private boolean testFichier = false;

    //private ArrayList<io.slack.model.User> amis = new ArrayList<io.slack.model.User>();
    //private ArrayList<io.slack.front.Chat> chats = new ArrayList<io.slack.front.Chat>();
    //surnom

    public User(String email, String mdp, String pseudo) {
        this.email = email;
        this.pseudo = pseudo;
        Date dateCreation= new Date();
        String date=""+dateCreation.getYear()+"-"+dateCreation.getMonth()+"-"+dateCreation.getDay();

        /*id=io.slack.dao.DatabaseUser.getDatabase().hacher("slack"+email+"@##");

        io.slack.dao.DatabaseUser.getDatabase().addUser(id,email, mdp, pseudo, date);*/
    }
    public User(String email, String pseudo){
        this.email=email;
        this.pseudo=pseudo;

        id= DatabaseUser.getDatabase().hacher("slack"+email+"@##");
    }

    public String getId() {return id;}
    public String getEmail() {return email;}
    public String getPseudo() {return pseudo;}
    public Image getPdp() {return pdp;}
    public void setPdp(Image pdp) {this.pdp = pdp;}


    public File getFichierJoint() {
        return fichierJoint;
    }

    public boolean isTestFichier() {
        return testFichier;
    }

    public void setFichierJoint(File fichierJoint) {
        this.fichierJoint = fichierJoint;
        this.testFichier=true;
    }
    public void resetFichierJoint(){
        this.fichierJoint = null;
        this.testFichier=false;
    }


    @Override
    public String toString() {
        return "io.slack.model.User{" +
                "email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", dateCreation="+
                '}';
    }

    public void editInfos(String email, String pseudo){
        this.pseudo=pseudo;
        if( Outils.isEmail(email) /*&& verif existence */){
            this.email=email;
        }
    }

    public void modifierInfos(String email, String pseudo) {
        this.pseudo=pseudo;
        if( Outils.isEmail(email) && ! DatabaseUser.getDatabase().existEmail(email)){
            this.email=email;
        }else{
            if( ! Outils.isEmail(email) )
                Fenetre.getFenetre().affichePopup(new String[] { "veuillez retaper un email au bon format [ exemple aaa@lip6.fr ]" } );
            if( DatabaseUser.getDatabase().existEmail(email) )
                Fenetre.getFenetre().affichePopup(new String[] { "email déjà utilisé par un utilisateur" } );

        }


        DatabaseUser.getDatabase().updateUser(this.id, this.email, this.pseudo);
        PageUser.getPage().repaint();
    }

    public void modifierPassword(String oldP, String newP) {
        String mdp = DatabaseUser.getDatabase().getPassword(id);
        if( oldP.equals(mdp)){
            if(Outils.isPassword(newP)){
                DatabaseUser.getDatabase().updatePassword(this.id, newP);

                PageUser.getPage().repaint();
            }else{
                Fenetre.getFenetre().affichePopup(new String[] {"veuillez taper un mot de passe au bon format  [ minimum 8 caractère : 1 maj | 1 min | 1 chiffre | 1 caractère spécial ]"} );
            }
        }else{
            Fenetre.getFenetre().affichePopup( new String[]{"le mot de passe ne correspond pas"} );
        }
    }
}
