package io.slack.model;

import java.util.Date;

public class Message {
    private String message = null;
    private Date dateEnvoie;
    private User auteur = null;

    public Message(User auteur, String message) {
        this.auteur=auteur;
        this.message = message;
        dateEnvoie = new Date();
    }

    public String getMessage() {
        return message;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAuteur() {
        return auteur;
    }
}
