package io.slack.network.communication;

import java.io.Serializable;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class MessageAttachment<T extends Serializable> extends Message {

	private final T attachment;

	public MessageAttachment (MessageAttachment<T> messageAttachment) {
		super(messageAttachment);
		this.attachment = messageAttachment.getAttachment();
	}

	public MessageAttachment(int code, T attachment) {
		super(code);
		this.attachment = attachment;
	}

	@Override
	public boolean hasAttachment() {
		return true;
	}

	public T getAttachment() {
		return attachment;
	}
}
