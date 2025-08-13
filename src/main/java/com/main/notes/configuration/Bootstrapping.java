package com.main.notes.configuration;

import com.main.notes.service.*;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableAspectJAutoProxy
public class Bootstrapping {
    @Bean
    public IMessageService createMessageService(EntityManager sqlExecutor, IUserService userService, IFileService fileService)
    {
        return new MessageService(sqlExecutor, userService, fileService);
    }

    @Bean
    public IFileService createFileService()
    {
        return new FileService();
    }

    @Bean
    public IUserService createUserService(EntityManager sqlExecutor)
    {
        return new UserService(sqlExecutor);
    }

    @Bean
    public UserDetailsManager createAuthenticationService(DataSource dataSource)
    {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain CreateLoginForm(HttpSecurity http)
    {
        try
        {
            http.authorizeHttpRequests(
                    config -> config.antMatchers(
                            "/js/**",
                                    "/css/**",
                                    "/images/**",
                                    "/notes/register",
                                    "/user/create")
                            .permitAll())
                    .formLogin(form -> form.loginPage("/notes/login")
                            .loginProcessingUrl("/authenticateTheUser")
                            .defaultSuccessUrl("/notes/panel", true)
                            .permitAll());
            return http.build();
        } catch(Exception ex)
        {
            return null;
        }
    }
}
