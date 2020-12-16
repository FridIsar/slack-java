package io.slack.front;

import io.slack.model.User;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message extends JPanel {
    private String message = null;
    private Date dateEnvoie;
    private User auteur = null;

    public Message(User auteur, String message) {
        setPreferredSize (new Dimension(1000, 100) ) ;
        setLayout(null);

        this.auteur=auteur;
        this.message = message;

        dateEnvoie = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JTextPane dessiner(){

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        //textPane
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setFontFamily(style, "Calibri");
        StyleConstants.setFontSize(style, 20);

        SimpleAttributeSet align = new SimpleAttributeSet();
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_LEFT);

        StyledDocument doc = textPane.getStyledDocument();

        try {
            if(auteur != null) {
                SimpleDateFormat simpleformat = new SimpleDateFormat("dd MMM yyyy, hh");
                StyleConstants.setFontSize(style, 12);
                doc.insertString(doc.getLength(), "\t" + auteur.getPseudo() + ", " + simpleformat.format(dateEnvoie) + "h\n", style);
            }
            StyleConstants.setFontSize(style, 20);
            doc.insertString(doc.getLength(), message+"\n", style );

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        return textPane;

    }
}
