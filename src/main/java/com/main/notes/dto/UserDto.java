package com.main.notes.dto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class UserDto {
    @Id
    @Column(name = "username")
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;

    @Column(name = "password")
    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

    @Column(name = "enabled")
    private int enabled = 1;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AuthorityDto authority;

    @Transient
    private String repeatingPassword;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEnabled(int enabled)
    {
        this.enabled = enabled;
    }

    public void setAuthority(AuthorityDto authority)
    {
        this.authority = authority;
    }

    public void setRepeatingPassword(String repeatingPassword)
    {
        this.repeatingPassword = repeatingPassword;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUsername()
    {
        return username;
    }

    public int getEnabled()
    {
        return enabled;
    }

    public AuthorityDto getAuthority()
    {
        return authority;
    }

    public String getRepeatingPassword()
    {
        return repeatingPassword;
    }
}
