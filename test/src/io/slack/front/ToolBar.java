package io.slack.front;

import io.slack.controller.Systeme;
import io.slack.utils.FileUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel implements ActionListener {
    private Image imgLogo = FileUtils.getImage("Icons/logo.png");
    private Image imgConnect = FileUtils.getImage("Icons/connect.png");
    private Image imgProfil = FileUtils.getImage("Icons/profil.jpg");
    private Image imgSearch = FileUtils.getImage("Icons/search.jpg");

    private JButton logo = new JButton(new ImageIcon(imgLogo.getScaledInstance(100,100, Image.SCALE_SMOOTH)));;
    private JTextField recherche = new JTextField("Rechercher");
    private JComboBox listeAmis = new JComboBox( /*////////////////// */);
    private JButton connect = new JButton(new ImageIcon(imgConnect.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
    private JButton profil = new JButton(new ImageIcon(imgProfil.getScaledInstance(100,100, Image.SCALE_SMOOTH)));
    private JButton search = new JButton(new ImageIcon(imgSearch.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));


    private static JToolBar barre = new JToolBar();
    private static ToolBar toolBar= new ToolBar();

    private ToolBar() {
        setPreferredSize (new Dimension(2000, 130) ) ;
        barre.setFloatable(false);
        //barre.setRollover(true);
        initMyButton();
        add(barre);
        addMyButton();
    }

    public void addMyButton(){
        barre.removeAll();
        barre.add( logo );
        barre.addSeparator(new Dimension(250,5));

        barre.add( listeAmis );
        barre.addSeparator(new Dimension(250,5));
        barre.add( recherche );
        barre.addSeparator(new Dimension(50,5));
        barre.add( search );
        barre.addSeparator(new Dimension(250,5));
        if( Systeme.isConnect() )
            barre.add( profil );
        else
            barre.add( connect );
        barre.addSeparator(new Dimension(100,5));
    }


    public static ToolBar getToolBar() { return toolBar; }

    public static JToolBar getBarre() { return barre; }

    public void initMyButton(){
        recherche.setPreferredSize(new Dimension(300, 0));
        recherche.setHorizontalAlignment(10);
        recherche.setFont(new Font("Rechercher", 0, 50));
        listeAmis.setPreferredSize(new Dimension(300,0));

        logo.addActionListener(this);
        connect.addActionListener(this);
        profil.addActionListener(this);
        search.addActionListener(this);

    }

    /*
    public JButton getLogo() { return this.logo; }
    public JTextField getRecherche() {return this.recherche; }
    public JComboBox getListeAmis() {return this.listeAmis; }
    public JButton getConnect() {return this.connect; }
    public JButton getProfil() {return this.profil;}
    public JButton getSearch() {return this.search;}
    */

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == connect){
            Object[] message = { "Avez vous un compte ?"};


            int option = JOptionPane.showConfirmDialog(Fenetre.getFenetre(), message, "authentification", JOptionPane.YES_NO_CANCEL_OPTION);
            if(option == JOptionPane.YES_OPTION){
                JTextField email = new JTextField();
                JTextField pwd = new JPasswordField();
                Object[] messageLogin = {"email :",email,"password :",pwd};

                int optionLogin = JOptionPane.showConfirmDialog(Fenetre.getFenetre(), messageLogin, "login", JOptionPane.OK_CANCEL_OPTION);

                if( optionLogin == JOptionPane.OK_OPTION)
                    Systeme.login(email.getText(), pwd.getText());
            }
            if(option == JOptionPane.NO_OPTION){
                JTextField pseudo = new JTextField();
                JTextField email = new JTextField();
                JTextField pwd = new JPasswordField();
                Object[] messageSignin = {"pseudo :", pseudo, "email ",email,"password: ",pwd};

                int optionSignin = JOptionPane.showConfirmDialog( Fenetre.getFenetre(), messageSignin, "signin", JOptionPane.OK_CANCEL_OPTION );

                if( optionSignin == JOptionPane.OK_OPTION )
                    Systeme.createAcc(pseudo.getText(), email.getText(), pwd.getText());
            }
        }

        if( source == logo){
            Fenetre.getFenetre().backToHome();
        }

        if(source == profil){
            PageUser.getPage().setContenu( Systeme.getUserCourant() );
            Fenetre.getFenetre().setContenu( PageUser.getPage() );
        }





    }

}
