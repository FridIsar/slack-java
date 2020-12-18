package io.slack.front.ui;

import io.slack.model.Message;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

public class UIMessage extends JPanel {
    protected Message message;

    public UIMessage(Message message) {
        setPreferredSize (new Dimension(1000, 100) ) ;
        setLayout(null);

        this.message=message;
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
            if(message.getAuteur() != null) {
                SimpleDateFormat simpleformat = new SimpleDateFormat("dd MMM yyyy, hh");
                StyleConstants.setFontSize(style, 12);
                doc.insertString(doc.getLength(), "\t" + message.getAuteur().getPseudo() + ", " + simpleformat.format(message.getDateEnvoie()) + "h\n", style);
            }
            StyleConstants.setFontSize(style, 20);
            doc.insertString(doc.getLength(), message.getMessage()+"\n", style );

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        return textPane;

    }
}
