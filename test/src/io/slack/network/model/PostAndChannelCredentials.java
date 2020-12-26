package io.slack.network.model;

import io.slack.model.Post;

import java.io.Serializable;

public class PostAndChannelCredentials implements Serializable {
    private Post post;
    private String channelTitle;

    public PostAndChannelCredentials() {  }

    public PostAndChannelCredentials(Post post, String channelTitle) {
        this.post = post;
        this.channelTitle = channelTitle;
    }

    public Post getPost() {
        return post;
    }

    public String getChannelTitle() {
        return channelTitle;
    }
}
