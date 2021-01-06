package io.slack.network.model;

import java.awt.*;
import java.io.Serializable;

public class PostAndChannelCredentials implements Serializable {
    private String authorEmail;
    private String textMessage;
    private Image attached;
    private String channelTitle;

    public PostAndChannelCredentials() {  }

    public PostAndChannelCredentials(String authorEmail, String textMessage, Image attached, String channelTitle) {
        this.authorEmail = authorEmail;
        this.textMessage = textMessage;
        this.attached = attached;
        this.channelTitle = channelTitle;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public Image getAttached() {
        return attached;
    }
    public boolean hasAttachment(){
        return attached!=null;
    }

    public String getChannelTitle() {
        return channelTitle;
    }
}
