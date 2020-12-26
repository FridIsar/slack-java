package io.slack.model;

import java.awt.Image;

public class PostImage extends Post {

    private Image image = null;

    public PostImage(User auteur, String message, Image image) {
        super(auteur, message);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

}
