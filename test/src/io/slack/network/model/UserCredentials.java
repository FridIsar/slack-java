package io.slack.network.model;

import java.io.Serializable;

public class UserCredentials implements Comparable<UserCredentials>, Serializable {
    private String email;
    private String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public int compareTo(UserCredentials credentials) {
        return 0;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
