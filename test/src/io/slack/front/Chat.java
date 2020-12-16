package io.slack.front;

import io.slack.controller.Systeme;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Chat extends PageCentrale implements ActionListener {
    private String titre;
    private ArrayList<Message> contenu = new ArrayList<Message>();
    //

    private Image image;
    //bouton
    private JButton send = new JButton("send");
    private JTextField texteMessage = new JTextField();
    private JButton joindre = new JButton("joindre");
    private JButton parametre = new JButton( "parametre");
    //
    private JTextPane textPane = new JTextPane();
    private JScrollPane jScrollPane= new JScrollPane(textPane);

    private boolean imagePresente = false;

    public Chat(String titre){
        setPreferredSize (new Dimension(1000, 850) ) ;
        setLayout(null);

        this.titre=titre;

        addContenu(new Message(Systeme.getUser()," Bienvenue, voici le début de la conversation '"+titre+"'"));


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


    public void addContenu(Message m){
        contenu.add(m);
    }

    public Image getImage() { return image;  }

    public void setImage(Image image) {  this.image = image; }

    public String getTitre() {return titre; }

    public void dessiner() throws BadLocationException {
        StyledDocument doc = textPane.getStyledDocument();
        textPane.getDocument().remove(0, doc.getLength() );
        for(int i=0; i<contenu.size(); i++){
            Message mess = contenu.get(i);
            textPane.insertComponent( mess.dessiner() );

            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setFontFamily(style, "Calibri");
            StyleConstants.setFontSize(style, 20);
            try {
                doc.insertString(doc.getLength(), "\n\n", style );
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }


        int tc=50;
        Font font = new Font("Arial",0,20);
        texteMessage.setBounds(0, 800, tc * 15, tc);
        texteMessage.setFont(font);
        send.setBounds(tc*15,800, tc * 2, tc);
        joindre.setBounds(tc*17, 800, tc*2, tc);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( source == send){
            /*if( Systeme.getUser().isTestFichier() ){
                addContenu( new MessageImage(Systeme.getUser(), texteMessage.getText(), Imagerie.getImage(Systeme.getUser().getFichierJoint()) ) );
                Systeme.getUser().resetFichierJoint();
            }else*/
                addContenu( new Message(Systeme.getUser(),texteMessage.getText()) );
            try {
                dessiner();
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
            //System.out.println(" send "+texteMessage.getText());
            texteMessage.setText("");

        }

        if(source == joindre){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.addChoosableFileFilter(new ImageFilter());
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.setDialogTitle("Choisir un fichier");
            int result = jFileChooser.showSaveDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
               // Systeme.getUser().setFichierJoint(jFileChooser.getSelectedFile());
            }
        }
    }
}
