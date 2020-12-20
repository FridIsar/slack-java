package io.slack.front;

import io.slack.controller.ControllerClient;
import io.slack.front.ui.UIUser;
import io.slack.model.User;
import io.slack.utils.FileUtils;
import io.slack.utils.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RightSidePanel extends JPanel implements ActionListener {
    private JButton settings = new JButton("settings");

    private JScrollPane jScrollPane;
    private static JToolBar bar = new JToolBar(JToolBar.VERTICAL);
    private static RightSidePanel panel = new RightSidePanel();

    private static ArrayList<JButton> listeBouton = new ArrayList<JButton>();

    private static final int DIMENSION_X = 300;
    private static final int DIMENSION_Y = 900;
    private static int DIMENSION_Y_BAR = 100;

    private RightSidePanel(){
        setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y) ) ;
        bar.setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y) ) ;
        setLayout(null);
        bar.setFloatable(false);

        initMyButton();
        addMyButton();

        jScrollPane = new JScrollPane(bar, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jScrollPane.setBounds(0,0,DIMENSION_X, DIMENSION_Y);
        add(jScrollPane);
    }

    public static RightSidePanel getPanel() {
        return panel;
    }

    public void addMyButton(){
        bar.removeAll();
        if( ControllerClient.isConnect() ){
            bar.add(settings);
        }
    }
    public void initMyButton(){
        settings.addActionListener(this);
        settings.setFont( new Font(Font.DIALOG,Font.BOLD,25));
    }


    public void addAUser(User user){
        Image image=null;
        //Image image = user; TODO add a profil picture to the model.User

        JButton button;
        if(image == null)
            image = FileUtils.getImage( "Icons/pictureProfil.png" );

        button = new JButton(new ImageIcon(image.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        button.setText(user.getPseudo());
        GraphicsUtils.buttonWithText(button);
        button.addActionListener(this);


        bar.setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y_BAR+=82 ));
        bar.add(button);
        listeBouton.add(button);
        //ControllerClient.addChat(chat);
    }
    public void removeAllUsers(){listeBouton.clear();}


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( source == settings){

        }

        for(JButton button : listeBouton){
            if(source == button){
                User user = ControllerClient.getUserTest(listeBouton.indexOf(button));
                Fenetre.getFenetre().setContenu( new UIUser(user) );

            }
        }
    }
}
