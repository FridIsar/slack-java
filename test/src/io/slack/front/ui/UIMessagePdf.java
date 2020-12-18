package io.slack.front.ui;

import io.slack.model.MessagePdf;

import java.io.File;

public class UIMessagePdf extends UIMessage {
    private File file;

    public UIMessagePdf(MessagePdf message) {
        super(message);
    }


}
