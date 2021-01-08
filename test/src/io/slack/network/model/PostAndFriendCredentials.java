package io.slack.network.model;

import java.awt.*;
import java.io.Serializable;

public class PostAndFriendCredentials implements Serializable {
    private String authorEmail;
    private String textMessage;
    private Image attached;
    private String userEmail;

    public PostAndFriendCredentials() { }

    public PostAndFriendCredentials(String authorEmail, String textMessage, Image attached, String userEmail) {
        this.authorEmail = authorEmail;
        this.textMessage = textMessage;
        this.attached = attached;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }
}
