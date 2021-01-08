package io.slack.front;

import io.slack.utils.FileUtils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame {
    private static JFrame frame = new JFrame();
    private static Window window = new Window();
    private static CentralePage center;

    private Window() {
        frame.setPreferredSize (new Dimension(2000, 1300) ) ;
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(FileUtils.getImage("Icons/logo.png"));
        frame.setTitle("Slack");
        frame.setFocusable(true);
        frame.setResizable(true);


        frame.setContentPane(new JPanel());

        setContenu( HomePage.getPage() );
        frame.setVisible(true);
        frame.pack();

    }

    public static Window getFenetre() {
        return window;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public void resetFrame() {
        frame = new JFrame();
        window = new Window();
    }

   public void setContenu(CentralePage contenu){
        if(center != null)
            frame.remove(center);
        center =contenu;
       try {
           center.dessiner();
       } catch (BadLocationException e) {
           e.printStackTrace();
       }
       frame.add(ToolBar.getToolBar(), BorderLayout.NORTH );
       frame.add(LeftSidePanel.getPanel(), BorderLayout.WEST);
       frame.add(center, BorderLayout.CENTER);
       frame.add(RightSidePanel.getPanel(), BorderLayout.EAST);
       frame.setVisible(true);
    }

    public void backToHome() {
        setContenu(HomePage.getPage());
    }


    public void affichePopup(String[] message){
        JOptionPane.showConfirmDialog(center, message, "ERROR", JOptionPane.OK_CANCEL_OPTION);
    }
    public int affichePopup(String[] message, String titre){
        return JOptionPane.showConfirmDialog(center, message, titre, JOptionPane.OK_CANCEL_OPTION);
    }
    public void refreshPage(){
        center.repaint();
    }
}
