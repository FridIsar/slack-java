package io.slack.front;

import io.slack.controller.ControllerClient;
import io.slack.front.ui.UIUser;
import io.slack.model.Friend;
import io.slack.model.User;
import io.slack.utils.FileUtils;
import io.slack.utils.GraphicsUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ToolBar extends JPanel implements ActionListener {
    private Image imgLogo = FileUtils.getImage("Icons/logo.png");
    private Image imgConnect = FileUtils.getImage("Icons/connect.png");
    private Image imgProfil = FileUtils.getImage("Icons/profil.png");
    private Image imgSearch = FileUtils.getImage("Icons/search.jpg");

    private JButton logo = new JButton(new ImageIcon(imgLogo.getScaledInstance(100,100, Image.SCALE_SMOOTH)));
    private JComboBox friendList = new JComboBox();
    private JButton connect = new JButton(new ImageIcon(imgConnect.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
    private JButton profil = new JButton(new ImageIcon(imgProfil.getScaledInstance(100,100, Image.SCALE_SMOOTH)));
    private JButton search = new JButton(new ImageIcon(imgSearch.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));


    private static JToolBar barre = new JToolBar();
    private static ToolBar toolBar= new ToolBar();

    private static ArrayList<User> userList = new ArrayList<>();

    private ToolBar() {
        setPreferredSize (new Dimension(2000, 130) ) ;
        barre.setFloatable(false);
        //barre.setLayout(null);
        //barre.setRollover(true);

        addMyButton();
        initMyButton();
        add(barre);
    }

    public void addMyButton(){
        barre.removeAll();
        barre.add( logo );
        barre.addSeparator(new Dimension(250,5));

        barre.add(friendList);
        barre.addSeparator(new Dimension(250,5));
        barre.addSeparator(new Dimension(50,5));
        barre.add( search );
        barre.addSeparator(new Dimension(250,5));
        if( ControllerClient.isConnect() )
            barre.add( profil );
        else
            barre.add( connect );
        barre.addSeparator(new Dimension(100,5));
    }


    public static ToolBar getToolBar() { return toolBar; }

    public static JToolBar getBarre() { return barre; }

    public void initMyButton(){
        friendList.setPreferredSize(new Dimension(300,0));

        /*
        logo.setBorderPainted(false);
        connect.setBorderPainted(false);
        profil.setBorderPainted(false);
        search.setBorderPainted(false);

        logo.setFocusPainted(false);
        connect.setFocusPainted(false);
        profil.setFocusPainted(false);
        search.setFocusPainted(false);
        */

        logo.addActionListener(this);
        connect.addActionListener(this);
        profil.addActionListener(this);
        search.addActionListener(this);

    }

    public void setFriendList(ArrayList<User> friends){
        for (User user1 : friends) {
            friendList.addItem(user1.getPseudo());
            userList.add(user1);
        }
    }

    public void addAFriend(User user){
        userList.add( user);
        friendList.addItem(user.getPseudo());
        addMyButton();
    }

    public void removeAFriend(int i){
        userList.remove(i);
        friendList.removeItemAt(i);
        addMyButton();
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == connect){
            Object[] message = { "Do you have a login account?"};


            int option = JOptionPane.showConfirmDialog(Window.getFenetre(), message, "authentification", JOptionPane.YES_NO_CANCEL_OPTION);
            if(option == JOptionPane.YES_OPTION){
                JTextField email = new JTextField();
                JTextField pwd = new JPasswordField();
                Object[] messageLogin = {"email :",email,"password :",pwd};

                int optionLogin = JOptionPane.showConfirmDialog(Window.getFenetre(), messageLogin, "login", JOptionPane.OK_CANCEL_OPTION);

                if( optionLogin == JOptionPane.OK_OPTION)
                    ControllerClient.login(email.getText(), pwd.getText());
            }
            if(option == JOptionPane.NO_OPTION){
                JTextField pseudo = new JTextField();
                JTextField email = new JTextField();
                JTextField pwd = new JPasswordField();
                Object[] messageSignin = {"pseudo :", pseudo, "email ",email,"password: ",pwd};

                int optionSignin = JOptionPane.showConfirmDialog( Window.getFenetre(), messageSignin, "signin", JOptionPane.OK_CANCEL_OPTION );

                if( optionSignin == JOptionPane.OK_OPTION )
                    ControllerClient.createAcc(pseudo.getText(), email.getText(), pwd.getText());
            }
        }

        if( source == logo){
            Window.getFenetre().backToHome();
            ControllerClient.resetCurrentChannel();
        }

        if(source == profil){
            ControllerClient.profil();
            ControllerClient.resetCurrentChannel();
        }

        if(source == search){
            User selectedUser = userList.get(this.friendList.getSelectedIndex());
            Window.getFenetre().setContenu(new UIUser(selectedUser));
        }





    }

}
