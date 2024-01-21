package com.example.personalfinancialmanagementsystem.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.personalfinancialmanagementsystem.pojo.user.ChangePasswordDto;
import com.example.personalfinancialmanagementsystem.pojo.user.User;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDto;

public interface UserService extends IService<User> {
    void deleteUserAllInformationById(String id);
    Page<UserDto> getUserPage(Integer page, Integer pageSize, String name) throws RuntimeException;

    Integer changePassword(ChangePasswordDto account);


}
