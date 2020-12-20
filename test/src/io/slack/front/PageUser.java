package io.slack.front;

import io.slack.controller.ControllerClient;
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


public class PageUser extends PageCentrale implements ActionListener {
    private static PageUser page = new PageUser();

    private JButton modifInfos = new JButton("modifier infos");
    private JButton modifPassword = new JButton("modifier password");
    private JButton deleteAccount = new JButton("delete account");
    private JButton disconnect = new JButton("se déconnecter");
    private JButton changePP = new JButton("devenir vendeur");
    

    private User contenu;



    private PageUser() {
        setPreferredSize(new Dimension(900,900));
        setLayout(null);

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

    public void dessiner() {

    }

    ///////////////////////////////////////////


    public static PageUser getPage() {
        return page;
    }
    public JButton getModifInfos() {
        return this.modifInfos;
    }
    public JButton getModifPassword() {
        return this.modifPassword;
    }
    public JButton getDeleteAccount() {return this.deleteAccount; }
    public JButton getDisconnect() { return this.disconnect; }

    public void setContenu(User value) { this.contenu = value;  }
    public User getContenu() { return this.contenu;}

    public static void afficheProfil(User u){
        page.contenu = u;
        Fenetre.getFenetre().setContenu(PageUser.getPage());
        Fenetre.setPageActuelle("profil");
    }


    ////////////////////////////////////////////////////////////


    public void paintComponent(Graphics g){
        addMyButton();
        super.paintComponent(g);

        Font font = new Font("Arial",0,35);

        int tc=50;


        for(int x=0; x<19; x++){
            for(int y=0; y<18; y++){
                g.drawRect(x*tc,y*tc,tc,tc) ;
            }
        }

        //nom
        JLabel labelNom = new JLabel("Bonjour "+contenu.getPseudo()+", bienvenue");
        labelNom.setBounds(4*tc, 0, tc*10, tc);
        labelNom.setFont(font);
        add(labelNom);

        font = new Font("Arial",0,20);

        //modifier infos
        modifInfos.setBounds(14*tc, 7*tc, tc*4, tc );
        modifInfos.setFont(font);
        //modifier password
        modifPassword.setBounds(13*tc, 16*tc, tc*5, tc );
        modifPassword.setFont(font);
        //supprimer compte
        deleteAccount.setBounds(0, 16*tc, tc*4, tc );
        deleteAccount.setFont(font);
        //disconnect
        disconnect.setBounds(14*tc, 0*tc, tc*4, tc );
        disconnect.setFont(font);

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
            JTextField email = new JTextField( contenu.getEmail());
            JTextField pseudo = new JTextField( contenu.getPseudo());
            Object[] message= {"email :", email, "pseudo :",pseudo};

            int option = JOptionPane.showConfirmDialog(this, message, "modifiez vos infos", JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION){
               // contenu.modifierInfos(email.getText(), pseudo.getText());
            }
        }

        if( source == modifPassword){
            JTextField oldPwd = new JPasswordField();
            JTextField newPwd = new JPasswordField();
            Object[] message = {"ancien mot de passe :", oldPwd, "nouveau mot de passe :", newPwd};

            int option = JOptionPane.showConfirmDialog(this, message, "modifiez votre mot de passe", JOptionPane.OK_CANCEL_OPTION );
            if(option==JOptionPane.OK_OPTION){
                //contenu.modifierPassword(oldPwd.getText(), newPwd.getText());
            }
        }

    }
}