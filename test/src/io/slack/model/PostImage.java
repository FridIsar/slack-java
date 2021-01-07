package io.slack.model;

import java.awt.Image;

public class PostImage extends Post {

    private Image image = null;

    public PostImage()  {}

    public PostImage(User auteur, String message, Channel channel, Image image) {
        super(auteur, message, channel);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

}
