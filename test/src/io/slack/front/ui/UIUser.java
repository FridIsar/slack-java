package io.slack.front.ui;

import io.slack.controller.ControllerClient;
import io.slack.front.CentralePage;
import io.slack.front.Window;
import io.slack.model.ChannelDirect;
import io.slack.model.User;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UIUser extends CentralePage implements ActionListener {
    //private static UIUser page = new UIUser();

    private JButton changeInfos = new JButton("change infos");
    private JButton changePassword = new JButton("change password");
    private JButton deleteAccount = new JButton("delete account");
    private JButton disconnect = new JButton("disconnect");
    private JButton changePP = new JButton("change profil pic");
    private JButton becomeFriend = new JButton("devenir ami");
    private JButton removeFriend = new JButton("retirer ami");
    private JButton directMessage = new JButton("message directe");
    

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
        add(changeInfos);
        add(changePassword);
        add(deleteAccount);
        add(disconnect);
        add(changePP);
        add(becomeFriend);
        add(removeFriend);
        add(directMessage);

    }

    public void initMyButton(){

        changeInfos.addActionListener(this);
        changePassword.addActionListener(this);
        deleteAccount.addActionListener(this);
        disconnect.addActionListener(this);
        changePP.addActionListener(this);
        becomeFriend.addActionListener(this);
        removeFriend.addActionListener(this);
        directMessage.addActionListener(this);
    }

    public void dessiner() {}

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
        if(user.equals(ControllerClient.getCurrentUser())) {
            JLabel labelNom = new JLabel("Hello " + user.getPseudo() + ", welcome");
            labelNom.setBounds(4 * tc, 0, tc * 10, tc);
            labelNom.setFont(font);
            add(labelNom);

            font = new Font("Arial", 0, 20);

            //modifier infos
            changeInfos.setBounds(14 * tc, 7 * tc, tc * 4, tc);
            changeInfos.setFont(font);
            //modifier password
            changePassword.setBounds(13 * tc, 16 * tc, tc * 5, tc);
            changePassword.setFont(font);
            //supprimer compte
            deleteAccount.setBounds(0, 16 * tc, tc * 4, tc);
            deleteAccount.setFont(font);
            //disconnect
            disconnect.setBounds(14 * tc, 0 * tc, tc * 4, tc);
            disconnect.setFont(font);
        }else{
            JLabel labelNom = new JLabel("Here is the account of " + user.getPseudo());
            labelNom.setBounds(4 * tc, 0, tc * 10, tc);
            labelNom.setFont(font);
            add(labelNom);

            font = new Font("Arial", 0, 20);

            if(! ControllerClient.isAFriend(user)) {
                becomeFriend.setBounds(6 * tc, 7 * tc, tc * 4, tc * 1);
                becomeFriend.setFont(font);
            }else{
                removeFriend.setBounds(6*tc, 7*tc, tc*4, tc*1);
                removeFriend.setFont(font);

                directMessage.setBounds(14 * tc, 0 * tc, tc * 4, tc);
                directMessage.setFont(font);
            }
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( source == disconnect){
            ControllerClient.disconnect(true);
        }

        if( source == deleteAccount){
            ControllerClient.deleteAccount();
        }

        if( source == changeInfos){
            JTextField email = new JTextField( user.getEmail());
            JTextField pseudo = new JTextField( user.getPseudo());
            Object[] message= {"email :", email, "pseudo :",pseudo};

            int option = JOptionPane.showConfirmDialog(this, message, "edit your information", JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION){
               ControllerClient.updateUser(user, email.getText(), pseudo.getText());
            }
        }

        if( source == changePassword){
            JTextField oldPwd = new JPasswordField();
            JTextField newPwd = new JPasswordField();
            Object[] message = {"old password :", oldPwd, "new password :", newPwd};

            int option = JOptionPane.showConfirmDialog(this, message, "edit your password", JOptionPane.OK_CANCEL_OPTION );
            if(option==JOptionPane.OK_OPTION){
                ControllerClient.updatePassword(user, oldPwd.getText(), newPwd.getText());
            }
        }

        if( source == becomeFriend){
            ControllerClient.addAFriend(user);
        }

        if( source == removeFriend){
            ControllerClient.deleteAFriend(user);
        }

        if( source == directMessage){
            ChannelDirect channelDirect = ControllerClient.getChannelDirectFriend(user);
            ControllerClient.setCurrentChannel(channelDirect);
            Window.getFenetre().setContenu( new UIChannel(channelDirect) );
        }

    }
}
