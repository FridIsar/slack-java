package io.slack.model;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private String message = null;
    private Date dateEnvoie;
    private User auteur = null;
    private Channel channel=null;

    public Post(){}

    public Post(User auteur, String message, Channel channel) {
        this.auteur=auteur;
        this.message = message;
        this.channel=channel;
        dateEnvoie = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
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
