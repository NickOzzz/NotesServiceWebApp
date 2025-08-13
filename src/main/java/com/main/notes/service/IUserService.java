package com.main.notes.service;

import com.main.notes.event.IUserEvent;
import com.main.notes.dto.UserDto;

import java.util.List;

public interface IUserService {
    IUserEvent CreateUser(UserDto user);
    List<UserDto> GetAllUsers();
    IUserEvent DeleteUser(String username);
    IUserEvent ChangePassword(UserDto user);
}
