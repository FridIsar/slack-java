package io.slack.model;

import java.io.Serializable;

public class Friend implements Serializable {
    private User firstUser;
    private User secUser;

    public Friend(){}

    public Friend(User firstUser, User secUser) {
        this.firstUser = firstUser;
        this.secUser = secUser;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public User getSecUser() {
        return secUser;
    }
}
