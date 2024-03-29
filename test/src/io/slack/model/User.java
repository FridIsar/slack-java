package io.slack.model;

import java.awt.*;
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
    private Image profilPic;

    public User() {}

    public User(String email, String password, String pseudo) {
        this.email = email;
        this.password = password;
        this.pseudo = pseudo;
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

    public void initCreatedAt() {
        this.createdAt = new Date(Instant.now().toEpochMilli());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Image getProfilPic() {
        return profilPic;
    }

    public void setProfilPic(Image profilPic) {
        this.profilPic = profilPic;
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
