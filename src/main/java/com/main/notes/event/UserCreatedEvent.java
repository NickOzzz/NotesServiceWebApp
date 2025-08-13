package com.main.notes.event;

public class UserCreatedEvent implements IUserEvent {
    private final String username;

    public UserCreatedEvent(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
