package com.example.personalfinancialmanagementsystem.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    private String name;
    private Integer gender;
    private String userId;
    private String id;
    private String imageName;
    private Integer age;
}
