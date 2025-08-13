package com.main.notes.event;

public class UserUpdatedEvent implements IUserEvent {
    private final String username;

    public UserUpdatedEvent(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
