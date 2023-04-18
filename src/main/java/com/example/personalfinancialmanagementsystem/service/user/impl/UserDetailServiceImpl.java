package com.example.personalfinancialmanagementsystem.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.mapper.user.UserDetailMapper;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDetail;
import com.example.personalfinancialmanagementsystem.service.user.UserDetailService;
import org.springframework.stereotype.Service;

/**
 *  ServiceImpl<UserDetailMapper, UserDetail>  也是mybatis - plus 提供的类
 *  mybatis 你需要自己在 mapper 类里 写sql或者写在配置文件中与mapper类实现一一映射  然后用
 *  ServiceImpl<UserDetailMapper, UserDetail>相当于是
 *  ServiceImpl BaseMapper Iservice
 *  mapper 和 service 主要的功能就是 持久化数据 / 查找 操作数据库
 *  mapper 提供的是 与数据库的对应关系,以及sql语句 但是他不进行操作
 *  service 使用 mapper 提供的这些 sql语句
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements UserDetailService {

}
