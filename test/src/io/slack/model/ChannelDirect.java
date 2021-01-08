package io.slack.model;

import java.util.List;
import java.util.Objects;

public class ChannelDirect extends Channel {

    private Friend friend;

    public ChannelDirect() {}

    public ChannelDirect(Friend friend) {
        this.friend = friend;
        friend.setChannelDirect(this);
    }

    public Friend getFriend() {
        return friend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChannelDirect that = (ChannelDirect) o;
        return (this.friend.getFirstUser().equals( that.friend.getFirstUser() )
                && this.friend.getSecUser().equals( that.friend.getSecUser() ) ) || (
                        this.friend.getFirstUser().equals( that.friend.getSecUser() )
                && this.friend.getSecUser().equals( that.friend.getFirstUser() ) );
    }
}
