package com.demosecurity.demosecurity.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private long id;
    private String name;
    private String password;
    private String role;
}
