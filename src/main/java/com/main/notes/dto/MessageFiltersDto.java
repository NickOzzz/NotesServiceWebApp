package com.main.notes.dto;

public class MessageFiltersDto {
    private String Username;
    private String ReceiverUsername;

    public MessageFiltersDto setUsername(String username)
    {
        this.Username = username;
        return this;
    }

    public String getUsername()
    {
        return Username;
    }

    public MessageFiltersDto setReceiverUsername(String username)
    {
        this.ReceiverUsername = username;
        return this;
    }

    public String getReceiverUsername()
    {
        return ReceiverUsername;
    }
}
