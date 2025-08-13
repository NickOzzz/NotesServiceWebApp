package com.main.notes.event;

public class UserFailedEvent implements IUserEvent {
    private final String username;

    public UserFailedEvent(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
