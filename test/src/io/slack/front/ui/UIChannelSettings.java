package io.slack.front.ui;

import io.slack.controller.ControllerClient;
import io.slack.front.CentralePage;
import io.slack.model.Channel;
import io.slack.utils.FileUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIChannelSettings extends CentralePage implements ActionListener {

    //button just for the admin
    private JButton addUser = new JButton("add user");
    private JButton removeUser = new JButton("remove user");
    private JButton deleteChannel = new JButton("delete channel");
    private JButton changePicture = new JButton("change picture");
    //button for all users
    private JButton mute = new JButton("mute");
    private JButton changeNickname = new JButton("change nickname");


    private Channel channel;

    public UIChannelSettings(Channel channel) {
        setPreferredSize(new Dimension(900,900));
        setLayout(null);

        this.channel = channel;

        initMyButton();
        addMyButton();
    }


    @Override
    public void addMyButton() {
        removeAll();
        if(channel.getAdmin().equals(ControllerClient.getCurrentUser()) ){
            add(addUser);
            add(removeUser);
            add(deleteChannel);
            add(changeNickname);
        }
        add(mute);
        add(changeNickname);
    }

    @Override
    public void initMyButton() {
        addUser.addActionListener(this);
        removeUser.addActionListener(this);
        deleteChannel.addActionListener(this);
        mute.addActionListener(this);
        changeNickname.addActionListener(this);
        changePicture.addActionListener(this);
    }

    @Override
    public void dessiner() throws BadLocationException {}

    public void paintComponent(Graphics g) {
        addMyButton();
        super.paintComponent(g);

        Font font = new Font("Arial",0,35);

        int tc=50;


        /*for(int x=0; x<19; x++){
            for(int y=0; y<18; y++){
                g.drawRect(x*tc,y*tc,tc,tc) ;
            }
        }*/

        JLabel labelTitle = new JLabel(channel.getTitle() +" settings");
        labelTitle.setBounds(4*tc, 0, tc*10, tc);
        labelTitle.setFont(font);
        add(labelTitle);

        g.drawImage(channel.getIcon()!=null ? channel.getIcon() : FileUtils.getImage( "Icons/logo.png" ), 1*tc, 0, tc*1, tc*1, this );

        font = new Font("Arial", 0, 20);

        changePicture.setBounds(1*tc, 2*tc, tc*4, tc*1 );
        changePicture.setFont(font);
        //
        deleteChannel.setBounds(0, 16 * tc, tc * 4, tc);
        deleteChannel.setFont(font);
        //
        mute.setBounds(14 * tc, 0 * tc, tc * 4, tc);
        mute.setFont(font);
        //
        changeNickname.setBounds(14 * tc, 7 * tc, tc * 4, tc);
        changeNickname.setFont(font);
        //
        addUser.setBounds(3*tc, 7*tc, tc*4, tc*1);
        addUser.setFont(font);
        //
        removeUser.setBounds(8*tc, 7*tc, tc*4, tc*1);
        removeUser.setFont(font);




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == addUser){
            JTextField userEmail = new JTextField();
            Object[] message = {"input email", userEmail};

            int option = JOptionPane.showConfirmDialog(this, message, "enter the email of the user to be added", JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION){
                ControllerClient.addUserInChannel(userEmail.getText(), channel);
            }
        }

        if(source == removeUser){
            JTextField userEmail = new JTextField();
            Object[] message = {"input email", userEmail};

            int option = JOptionPane.showConfirmDialog(this, message, "enter the email of the user to be removed", JOptionPane.OK_CANCEL_OPTION);

            if(option == JOptionPane.OK_OPTION){
                ControllerClient.removeUserInChannel(userEmail.getText(), channel);
            }
        }

        if(source == deleteChannel){
            int option = JOptionPane.showConfirmDialog(this, "delete this channel permanently", "delete channel", JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                ControllerClient.deleteChannel();
            }
        }

        if(source == changePicture){

        }

        if(source == mute){

        }

        if(source == changeNickname){

        }
    }
}
