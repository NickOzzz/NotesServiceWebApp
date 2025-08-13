package com.main.notes.event;

public class MessageDeletedEvent implements IMessageEvent {
    private String messageId;

    public MessageDeletedEvent(String messageId)
    {
        this.messageId = messageId;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public String getMessageId()
    {
        return this.messageId;
    }

    public String getMessage()
    {
        return "Deleted message with id " + this.messageId;
    }
}
