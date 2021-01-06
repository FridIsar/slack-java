package io.slack.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

public class User implements Comparable<User>, Serializable {

    private String email;
    private String password;
    private String pseudo;
    private Date createdAt;
    private int id;

    public User() {}

    public User(String email, String password, String pseudo) {
        this.email = email;
        this.password = password;
        this.pseudo = pseudo;
        this.createdAt = new Date(Instant.now().toEpochMilli());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", createdAt=" + createdAt +
                ", id=" + id +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return pseudo.compareToIgnoreCase(o.pseudo);
    }

}
