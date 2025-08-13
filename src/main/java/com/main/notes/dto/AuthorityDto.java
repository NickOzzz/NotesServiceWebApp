package com.main.notes.dto;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class AuthorityDto {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private UserDto user;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setAuthority(Authorities authority)
    {
        this.authority = authority.toString();
    }

    public String getUsername()
    {
        return username;
    }

    public String getAuthority()
    {
        return authority;
    }
}
