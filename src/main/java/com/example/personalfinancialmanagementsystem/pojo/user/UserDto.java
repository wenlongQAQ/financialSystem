package com.example.personalfinancialmanagementsystem.pojo.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto extends User {
    private Boolean warning;
}
