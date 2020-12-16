package io.slack.front;

import io.slack.model.User;

import java.io.File;

public class MessagePdf extends Message {
    private File file;

    public MessagePdf(User auteur, String message, File file) {
        super(auteur, message);
        this.file = file;
    }


}
