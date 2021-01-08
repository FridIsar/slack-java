package io.slack.model;

public class PostDirect extends Post {
    private Friend friend;

    public PostDirect(){}

    public PostDirect(User author, String message, Friend friend) {
        super(author, message, friend.getChannelDirect());
        this.friend=friend;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }
}
