package com.example.personalfinancialmanagementsystem.pojo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private Integer identity;
    private String phoneNum;
    private String email;
    @TableField(exist = false)
    private UserDetail userDetail;
}
