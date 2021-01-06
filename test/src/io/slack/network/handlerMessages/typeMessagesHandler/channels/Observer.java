package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.network.communication.Message;

public interface Observer {

    public void notify(Message messageNotify);
}
