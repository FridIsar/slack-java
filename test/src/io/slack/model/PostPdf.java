package io.slack.model;


import java.io.File;

public class PostPdf extends Post {
    private File file;

    public PostPdf(User auteur, String message, Channel channel, File file) {
        super(auteur, message, channel);
        this.file = file;
    }


}
