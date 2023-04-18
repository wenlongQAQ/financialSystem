package com.example.personalfinancialmanagementsystem.pojo.user;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String  userId;
}
