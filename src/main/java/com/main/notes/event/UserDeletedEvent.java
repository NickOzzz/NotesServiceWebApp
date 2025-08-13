package com.main.notes.event;

public class UserDeletedEvent implements IUserEvent {
    private final String username;

    public UserDeletedEvent(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
}
