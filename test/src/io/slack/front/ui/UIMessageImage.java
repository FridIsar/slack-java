package io.slack.front.ui;

import io.slack.model.PostImage;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import java.awt.Image;

public class UIMessageImage extends UIMessage {

    public UIMessageImage(PostImage messageImage) {
        super(messageImage);
    }

    @Override
    public JTextPane dessiner() {
        JTextPane textPane = super.dessiner();

        ImageIcon img = new ImageIcon(((PostImage) post).getImage().getScaledInstance(200,200, Image.SCALE_SMOOTH));
        textPane.insertIcon( img );
        return textPane;

    }
}
