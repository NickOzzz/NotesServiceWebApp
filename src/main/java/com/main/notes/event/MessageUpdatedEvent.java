package com.main.notes.event;

public class MessageUpdatedEvent implements IMessageEvent {
    private String messageId;
    private String message;

    public MessageUpdatedEvent(String messageId, String message)
    {
        this.messageId = messageId;
        this.message = message;
    }

    public String getMessageId()
    {
        return this.messageId;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
