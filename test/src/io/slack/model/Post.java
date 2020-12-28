package io.slack.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;

public class Post implements Serializable {
    private String message = null;
    private Date sendingDate;
    private User auteur = null;
    private Channel channel=null;

    public Post(){}

    public Post(User auteur, String message, Channel channel) {
        this.auteur=auteur;
        this.message = message;
        this.channel=channel;
        sendingDate = new Date(Instant.now().toEpochMilli());
    }

    public String getMessage() {
        return message;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAuteur() {
        return auteur;
    }

    public Channel getChannel() {
        return channel;
    }
}
