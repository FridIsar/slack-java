package io.slack.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;

public class Post implements Serializable {
    private String message = null;
    private Date sendingDate;
    private User author = null;
    private Channel channel=null;
    private int id;
    private Date modificationDate=null;

    public Post(){}

    public Post(User author, String message, Channel channel) {
        this.author = author;
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

    public User getAuthor() {
        return author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Channel getChannel() {
        return channel;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "message='" + message + '\'' +
                ", sendingDate=" + sendingDate +
                ", author=" + author.getEmail() +
                ", channel=" + channel.getTitle() +
                ", id=" + id +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
