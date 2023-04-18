package com.example.personalfinancialmanagementsystem.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.personalfinancialmanagementsystem.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
