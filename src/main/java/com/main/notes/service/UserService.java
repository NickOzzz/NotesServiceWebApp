package com.main.notes.service;

import com.main.notes.event.*;
import com.main.notes.dto.Authorities;
import com.main.notes.dto.AuthorityDto;
import com.main.notes.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserService implements IUserService {
    private final EntityManager sqlExecutor;
    private final String EncryptionType = "{bcrypt}";

    @Autowired
    public UserService(EntityManager sqlExecutor)
    {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    @Transactional
    public IUserEvent CreateUser(UserDto user)
    {
        String username = user.getUsername();
        try
        {
            EncodeUserDtoPassword(user);
            SetUserDtoAuthority(user);
            sqlExecutor.persist(user);

            return new UserCreatedEvent(username);
        }
        catch(Exception ex)
        {
            return new UserFailedEvent(username);
        }
    }

    @Override
    @Transactional
    public List<UserDto> GetAllUsers()
    {
        Query query = sqlExecutor.createQuery("FROM UserDto");
        return query.getResultList();
    }

    @Override
    @Transactional
    public IUserEvent DeleteUser(String username)
    {
        try
        {
            UserDto user = sqlExecutor.find(UserDto.class, username);
            sqlExecutor.remove(user);

            return new UserDeletedEvent(username);
        }
        catch (Exception ex)
        {
            return new UserFailedEvent(username);
        }
    }

    @Override
    @Transactional
    public IUserEvent ChangePassword(UserDto user)
    {
        String username = user.getUsername();
        try
        {
            EncodeUserDtoPassword(user);
            sqlExecutor.merge(user);

            return new UserUpdatedEvent(username);
        }
        catch(Exception ex)
        {
            return new UserFailedEvent(username);
        }
    }

    private void EncodeUserDtoPassword(UserDto user)
    {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(EncryptionType + encodedPassword);
    }

    private void SetUserDtoAuthority(UserDto user)
    {
        AuthorityDto authority = new AuthorityDto();
        authority.setAuthority(Authorities.USER);
        authority.setUsername(user.getUsername());
        user.setAuthority(authority);
    }
}
