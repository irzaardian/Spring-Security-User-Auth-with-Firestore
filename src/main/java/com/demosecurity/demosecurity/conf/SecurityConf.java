package com.demosecurity.demosecurity.conf;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.demosecurity.demosecurity.entities.Account;
import com.demosecurity.demosecurity.handlerexc.DocumentNotFoundException;
import com.demosecurity.demosecurity.service.MainService;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
    @Autowired
    private MainService mainService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/get", "/getData", "/create").permitAll()
                .antMatchers("/getAdmin").hasRole("ADMIN")
                .antMatchers("/getUser").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userManager = new InMemoryUserDetailsManager();
        try {
            List<Account> data = mainService.getAllData();
            for (int i = 0; i < data.size(); i++) {
                userManager
                        .createUser(User.withDefaultPasswordEncoder().username(data.get(i).getName())
                                .password(data.get(i).getPassword()).roles(data.get(i).getRole()).build());
            }
        } catch (InterruptedException | ExecutionException | DocumentNotFoundException e) {
            e.printStackTrace();
        }
        return userManager;
    }
}