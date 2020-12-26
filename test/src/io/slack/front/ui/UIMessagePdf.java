package io.slack.front.ui;

import io.slack.model.PostPdf;

import java.io.File;

public class UIMessagePdf extends UIMessage {
    private File file;

    public UIMessagePdf(PostPdf message) {
        super(message);
    }


}
