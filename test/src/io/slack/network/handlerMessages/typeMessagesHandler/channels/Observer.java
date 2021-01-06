package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.network.communication.Message;

public interface Observer {

    public void notify(Message messageNotify);
}
