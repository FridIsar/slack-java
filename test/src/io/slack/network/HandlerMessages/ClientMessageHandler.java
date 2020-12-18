package io.slack.network.HandlerMessages;

import io.slack.network.ClientTreatment;
import io.slack.network.communication.Message;

import java.io.Serializable;

public interface ClientMessageHandler<T extends Serializable> {

    Message handle(T dataMessage, ClientTreatment clientTreatment);
}
