package io.slack.network.model;


import java.io.Serializable;

public class UserCredentialsOptions extends UserCredentials implements Serializable {
    private String pseudo;

    public UserCredentialsOptions(String email, String password, String pseudo) {
        super(email, password);
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }
}
