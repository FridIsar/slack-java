package io.slack.front;

import io.slack.controller.ControllerClient;
import io.slack.front.ui.UIChannel;
import io.slack.model.Channel;
import io.slack.utils.FileUtils;
import io.slack.utils.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LeftSidePanel extends JPanel implements ActionListener {
    private JButton createChat = new JButton("create chat");

    private JScrollPane jScrollPane;
    private static JToolBar bar = new JToolBar(JToolBar.VERTICAL);
    private static LeftSidePanel panel = new LeftSidePanel();

    private static ArrayList<JButton> listeBouton = new ArrayList<JButton>();

    private Image imgNotif = FileUtils.getImage("Chat/red.png");

    //private int chatCompteur = 0;

    private static final int DIMENSION_X = 300;
    private static final int DIMENSION_Y = 900;

    private static int DIMENSION_Y_BAR = 100;



    private LeftSidePanel(){
        setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y) ) ;
        bar.setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y) ) ;
        setLayout(null);
        bar.setFloatable(false);
        //bar.setLayout(new GridLayout(50,1));

        initMyButton();
        addMyButton();

        jScrollPane = new JScrollPane(bar, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jScrollPane.setBounds(0,0,DIMENSION_X, DIMENSION_Y);
        add(jScrollPane);
    }

    public static ArrayList<JButton> getListeBouton() {
        return listeBouton;
    }

    public static LeftSidePanel getPanel() {return panel; }

    public void addMyButton(){
        bar.removeAll();
        if( ControllerClient.isConnect() ){
            bar.add(createChat);
        }
    }

    public void resetList() {
        bar.removeAll();
        listeBouton.clear();
        bar.setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y_BAR=DIMENSION_Y ));
        bar.add(createChat);
    }

    public void initMyButton(){
        createChat.addActionListener(this);
        createChat.setFont( new Font(Font.DIALOG,Font.BOLD,25));
    }

    public void refreshList(ArrayList<Channel> channels)    {
        resetList();
        for (Channel channel : channels)    {
            addAChat(channel);
        }
    }

    public void addAChat(Channel chat){
        Image image = chat.getIcon();

        JButton button;
        if (image == null) {
            image = FileUtils.getImage( "Icons/logo.png" );
        }
        button = new JButton(new ImageIcon(image.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        button.setText(chat.getTitle());
        GraphicsUtils.buttonWithText(button);
        button.addActionListener(this);


        bar.setPreferredSize(new Dimension(DIMENSION_X, DIMENSION_Y_BAR+=82 ));
        bar.add(button);
        listeBouton.add(button);
    }

    public void addNotif(int i){
        JButton notif = new JButton(new ImageIcon(imgNotif.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        GraphicsUtils.notification(notif);
        listeBouton.get(i).add(notif);
    }
    public void removeNotif(int i){
        listeBouton.get(i).removeAll();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( source == createChat ){
            JTextField titre = new JTextField();
            titre.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if(titre.getText().length()>=15 ) //limit textfield to 11 characters
                        e.consume();
                }
            });

            int option = JOptionPane.showConfirmDialog( Window.getWindow(), titre, "créer votre chat", JOptionPane.OK_CANCEL_OPTION );
            if(option == JOptionPane.OK_OPTION){
                ControllerClient.createChannel(titre.getText());
            }
        }

        for(JButton button : listeBouton){
            if( source == button ){
                Channel channel = ControllerClient.getChannel( listeBouton.indexOf(button));
                ControllerClient.setCurrentChannel(channel);
                Window.getWindow().setContenu( new UIChannel(channel));
            }
        }
    }
}
