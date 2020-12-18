package io.slack.front.ui;

import io.slack.controller.ControllerClient;
import io.slack.front.Fenetre;
import io.slack.front.CentralePage;
import io.slack.model.User;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UIUser extends CentralePage implements ActionListener {
    //private static UIUser page = new UIUser();

    private JButton modifInfos = new JButton("modifier infos");
    private JButton modifPassword = new JButton("modifier password");
    private JButton deleteAccount = new JButton("delete account");
    private JButton disconnect = new JButton("se déconnecter");
    private JButton changePP = new JButton("devenir vendeur");
    

    private User user;



    public UIUser(User user) {
        setPreferredSize(new Dimension(900,900));
        setLayout(null);

        this.user=user;
        initMyButton();

        addMyButton();

    }
    public void addMyButton(){
        removeAll();
        add(modifInfos);
        add(modifPassword);
        add(deleteAccount);
        add(disconnect);
        add(changePP);

    }

    public void initMyButton(){

        modifInfos.addActionListener(this);
        modifPassword.addActionListener(this);
        deleteAccount.addActionListener(this);
        disconnect.addActionListener(this);
        changePP.addActionListener(this);
    }

    public void dessiner() {}

    ///////////////////////////////////////////

/*
    public static UIUser getPage() {
        return page;
    }

    public User getContenu() { return this.user;}

    public void setContenu(User contenu) {
        this.user = contenu;
    }

    public static void afficheProfil(User u){
        page.contenu = u;
        Fenetre.getFenetre().setContenu(UIUser.getPage());
        //Fenetre.setPageActuelle("profil");
    }

 */


    ////////////////////////////////////////////////////////////


    public void paintComponent(Graphics g){
        addMyButton();
        super.paintComponent(g);

        Font font = new Font("Arial",0,35);

        int tc=50;


        /*for(int x=0; x<19; x++){
            for(int y=0; y<18; y++){
                g.drawRect(x*tc,y*tc,tc,tc) ;
            }
        }*/

        //nom
        if(user.equals(ControllerClient.getUserCourant())) {
            JLabel labelNom = new JLabel("Bonjour " + user.getPseudo() + ", bienvenue");
            labelNom.setBounds(4 * tc, 0, tc * 10, tc);
            labelNom.setFont(font);
            add(labelNom);

            font = new Font("Arial", 0, 20);

            //modifier infos
            modifInfos.setBounds(14 * tc, 7 * tc, tc * 4, tc);
            modifInfos.setFont(font);
            //modifier password
            modifPassword.setBounds(13 * tc, 16 * tc, tc * 5, tc);
            modifPassword.setFont(font);
            //supprimer compte
            deleteAccount.setBounds(0, 16 * tc, tc * 4, tc);
            deleteAccount.setFont(font);
            //disconnect
            disconnect.setBounds(14 * tc, 0 * tc, tc * 4, tc);
            disconnect.setFont(font);
        }else{
            JLabel labelNom = new JLabel("Voici le compte de " + user.getPseudo());
            labelNom.setBounds(4 * tc, 0, tc * 10, tc);
            labelNom.setFont(font);
            add(labelNom);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( source == disconnect){
            ControllerClient.disconnect();
        }

        if( source == deleteAccount){
            ControllerClient.deleteAccount();
        }

        if( source == modifInfos ){
            JTextField email = new JTextField( user.getEmail());
            JTextField pseudo = new JTextField( user.getPseudo());
            Object[] message= {"email :", email, "pseudo :",pseudo};

            int option = JOptionPane.showConfirmDialog(this, message, "modifiez vos infos", JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION){
               ControllerClient.updateUser(user, email.getText(), pseudo.getText());
            }
        }

        if( source == modifPassword){
            JTextField oldPwd = new JPasswordField();
            JTextField newPwd = new JPasswordField();
            Object[] message = {"ancien mot de passe :", oldPwd, "nouveau mot de passe :", newPwd};

            int option = JOptionPane.showConfirmDialog(this, message, "modifiez votre mot de passe", JOptionPane.OK_CANCEL_OPTION );
            if(option==JOptionPane.OK_OPTION){
                ControllerClient.updatePassword(user, oldPwd.getText(), newPwd.getText());
            }
        }

    }
}
