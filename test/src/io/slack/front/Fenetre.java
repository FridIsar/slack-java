package io.slack.front;

import io.slack.utils.FileUtils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Fenetre extends JFrame implements KeyListener, ActionListener {
    private static JFrame frame = new JFrame();
    private static Fenetre fenetre = new Fenetre();
    private static PageCentrale centre;
    private static Container panneauGauche;
    private static String pageActuelle = "accueil";

    private Fenetre() {
        frame.setPreferredSize (new Dimension(2000, 1300) ) ;
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(FileUtils.getImage("Icons/logo.png"));
        frame.setTitle("Slack");
        frame.setFocusable(true);
        frame.setResizable(true);
        frame.addKeyListener(this);


        frame.setContentPane(new JPanel());

        setContenu( PageAccueil.getPage() );
        //frame.add(io.slack.front.ToolBar.getToolBar(), BorderLayout.NORTH );
        //frame.add(io.slack.front.PanneauLateralGauche.getPanneau(), BorderLayout.WEST);
        //frame.add(centre, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.pack();

    }

    public static Fenetre getFenetre() {
        return fenetre;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public void resetFrame() {
        frame = new JFrame();
        fenetre = new Fenetre();
    }

    public static void setPageActuelle(String value) {
        pageActuelle = value;
    }

    public String getPageActuelle() {
        return pageActuelle;
    }

   public void setContenu(PageCentrale contenu){
        if(centre != null)
            frame.remove(centre);
        centre=contenu;
       try {
           centre.dessiner();
       } catch (BadLocationException e) {
           e.printStackTrace();
       }
       frame.add(ToolBar.getToolBar(), BorderLayout.NORTH );
       frame.add(PanneauLateralGauche.getPanneau(), BorderLayout.WEST);
       frame.add(centre, BorderLayout.CENTER);
       frame.setVisible(true);
    }

    public void backToHome() {
        setContenu(PageAccueil.getPage());
    }


    public void affichePopup(String[] message){
        JOptionPane.showConfirmDialog(centre, message, "ERROR", JOptionPane.OK_CANCEL_OPTION);
    }
    public int affichePopup(String[] message, String titre){
        return JOptionPane.showConfirmDialog(centre, message, titre, JOptionPane.OK_CANCEL_OPTION);
    }
    public void refreshPage(){
        centre.repaint();
    }


    ////////////////////////////////////////////////////////


    public void actionPerformed(ActionEvent e){

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
