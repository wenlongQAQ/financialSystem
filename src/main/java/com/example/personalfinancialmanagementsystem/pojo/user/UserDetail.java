package com.example.personalfinancialmanagementsystem.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

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
    private Date birth;
}
