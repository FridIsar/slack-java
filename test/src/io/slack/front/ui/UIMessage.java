package io.slack.front.ui;

import io.slack.model.Post;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

public class UIMessage extends JPanel {
    protected Post post;

    public UIMessage(Post post) {
        setPreferredSize (new Dimension(1000, 100) ) ;
        setLayout(null);

        this.post = post;
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
            if(post.getAuteur() != null) {
                SimpleDateFormat simpleformat = new SimpleDateFormat("dd MMM yyyy, hh");
                StyleConstants.setFontSize(style, 12);
                doc.insertString(doc.getLength(), "\t" + post.getAuteur().getPseudo() + ", " + simpleformat.format(post.getSendingDate()) + "h\n", style);
            }
            StyleConstants.setFontSize(style, 20);
            doc.insertString(doc.getLength(), post.getMessage()+"\n", style );

        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        return textPane;

    }
}
