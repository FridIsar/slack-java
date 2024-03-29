package io.slack.front.ui;

import io.slack.controller.ControllerClient;
import io.slack.front.ImageFilter;
import io.slack.front.CentralePage;
import io.slack.model.Channel;
import io.slack.model.ChannelDirect;
import io.slack.model.Post;
import io.slack.model.PostImage;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIChannel extends CentralePage implements ActionListener {
    private Channel channel;

    //bouton
    private JButton send = new JButton("send");
    private JTextField texteMessage = new JTextField();
    private JButton joindre = new JButton("joindre");
    //
    private JTextPane textPane = new JTextPane();
    private JScrollPane jScrollPane= new JScrollPane(textPane);

    private boolean imagePresente = false;

    public UIChannel(Channel channel){
        setPreferredSize (new Dimension(1000, 850) ) ;
        setLayout(null);

        this.channel=channel;

        initMyButton();
        addMyButton();

        try {
            dessiner();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        add(jScrollPane);
        jScrollPane.setBounds(0,0,1000,800);
        textPane.setEditable(false);
        //textPane.setBackground(new Color(108, 132, 203, 121));
    }


    public void addMyButton(){
        add(send);
        add(texteMessage);
        add(joindre);
    }
    public void initMyButton(){
        send.addActionListener(this);
        joindre.addActionListener(this);
    }


    public Image getIcon() { return channel.getIcon();  }

    public void setIcon(Image image) {  channel.setIcon(image); }



    public void dessiner() throws BadLocationException {
        //clear the pane
        StyledDocument doc = textPane.getStyledDocument();
        textPane.getDocument().remove(0, doc.getLength() );
        //add UIMessage to the pane
        for(int i=0; i<channel.getMessages().size(); i++){
            Post post = channel.getMessages().get(i);

            JTextPane messageUI;
            if(post instanceof PostImage){
                messageUI = new UIMessageImage((PostImage) post).dessiner();
            }else{
                messageUI = new UIMessage(post).dessiner();
            }
            textPane.insertComponent(messageUI);
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setFontFamily(style, "Calibri");
            StyleConstants.setFontSize(style, 20);
            try {
                doc.insertString(doc.getLength(), "\n\n", style );
                System.out.println("inserting");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        int tc=50;
        Font font = new Font("Arial",0,20);
        texteMessage.setBounds(0, 800, tc * 15, tc);
        texteMessage.setFont(font);
        send.setBounds(tc*15,800, tc * 2, tc);
        joindre.setBounds(tc*17, 800, tc*2, tc);
        //System.out.println(doc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( source == send){
            if(channel instanceof ChannelDirect)
                ControllerClient.sendPostFriend(((ChannelDirect) channel).getFriend(), texteMessage.getText());
            else
                ControllerClient.sendPost(channel,texteMessage.getText());
            try {
                dessiner();
                dessiner();
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
            texteMessage.setText("");

        }

        if(source == joindre){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.addChoosableFileFilter(new ImageFilter());
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.setDialogTitle("Choose a file");
            int result = jFileChooser.showSaveDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
               ControllerClient.setAttachedFile(jFileChooser.getSelectedFile());
            }
        }
    }
}
