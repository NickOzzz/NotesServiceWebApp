package com.main.notes.dto;

import java.util.List;

public class GetAllMessagesDto {
    public List<MessageDto> messages;
    public List<String> creators;

    public List<MessageDto> getMessages() {
        return messages;
    }

    public List<String> getCreators() {
        return creators;
    }

    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }
}
