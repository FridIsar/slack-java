package io.slack.network.model;

import java.io.Serializable;

public class UserAndUserCredentials implements Comparable<UserAndUserCredentials>, Serializable {

    private String firstUserEmail;
    private String secondUserEmail;

    public UserAndUserCredentials() {
    }

    public UserAndUserCredentials(String firstUserEmail, String secondUserEmail) {
        this.firstUserEmail = firstUserEmail;
        this.secondUserEmail = secondUserEmail;
    }

    public String getFirstUserEmail() {
        return firstUserEmail;
    }

    public String getSecondUserEmail() {
        return secondUserEmail;
    }

    @Override
    public int compareTo(UserAndUserCredentials o) {
        return 0;
    }
}
