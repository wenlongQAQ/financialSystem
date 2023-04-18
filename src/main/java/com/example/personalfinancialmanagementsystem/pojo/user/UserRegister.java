package com.example.personalfinancialmanagementsystem.pojo.user;

import lombok.Data;

@Data // toString() set get
public class UserRegister {
    private String username;
    private String password;
    private Integer identity;
    private String phoneNum;
    private String email;
    private String code;
}
