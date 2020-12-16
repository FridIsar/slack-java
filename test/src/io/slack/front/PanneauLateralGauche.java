package io.slack.front;

import io.slack.controller.Systeme;
import io.slack.utils.FileUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanneauLateralGauche extends JPanel implements ActionListener {
    private JButton createChat = new JButton("create chat");

    private JScrollPane jScrollPane;
    private static JToolBar barre = new JToolBar(JToolBar.VERTICAL);
    private static PanneauLateralGauche panneau = new PanneauLateralGauche();

    private static ArrayList<JButton> listeBouton = new ArrayList<JButton>();

    private int chatCompteur = 0;


    private PanneauLateralGauche(){
        setPreferredSize (new Dimension(150, 900) ) ;
        setLayout(null);
        barre.setFloatable(false);
        barre.setLayout(new GridLayout(50,1));
        //barre.setPreferredSize(new Dimension(150, 900));

        initMyButton();
        addMyButton();

        jScrollPane = new JScrollPane(barre, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jScrollPane.setBounds(0,0,150,900);
        add(jScrollPane);

    }

    public static PanneauLateralGauche getPanneau() {return panneau; }

    public void addMyButton(){
        barre.removeAll();
        if( Systeme.isConnect() ){
            barre.add(createChat);
        }
    }
    public void initMyButton(){
        createChat.addActionListener(this);
    }

    public void addAChat(Chat chat){
        if(chatCompteur < 50) {
            Image image = chat.getImage();

            JButton bouton;
            if (image == null) {
                image = FileUtils.getImage( "Icons/logo.png" );
            }
            bouton = new JButton(new ImageIcon(image.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
            bouton.addActionListener(this);

            barre.add(bouton);
            listeBouton.add(bouton);
            Systeme.addChat(chat);

            chatCompteur++;

        }else{
            Fenetre.getFenetre().affichePopup(new String[]{ "limite de chat atteinte" });
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( source == createChat ){
            JTextField titre = new JTextField();

            int option = JOptionPane.showConfirmDialog( Fenetre.getFenetre(), titre, "créer votre chat", JOptionPane.OK_CANCEL_OPTION );
            if(option == JOptionPane.OK_OPTION){
                Chat chat = new Chat(titre.getText());

                this.addAChat( chat );
            }
        }

        for(JButton button : listeBouton){
            if( source == button ){
                Chat c = Systeme.getChat( listeBouton.indexOf(button));
                //System.out.println("chat : "+c.getTitre());
                Fenetre.getFenetre().setContenu( c );
            }
        }
    }
}
