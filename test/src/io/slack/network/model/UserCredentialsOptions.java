package io.slack.network.model;


import java.io.Serializable;

public class UserCredentialsOptions extends UserCredentials implements Serializable {
    private String pseudo;
    private int id;
    public UserCredentialsOptions() {}
    public UserCredentialsOptions(String email, String password, String pseudo) {
        super(email, password);
        this.pseudo = pseudo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }
}
