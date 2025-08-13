package com.main.notes.event;

public class MessageCreatedEvent implements IMessageEvent {
    private final String MessageId;

    public MessageCreatedEvent(String MessageId)
    {
        this.MessageId = MessageId;
    }

    public String getMessage()
    {
        return "Created message successfully with Id " + this.MessageId;
    }
}
