package com.example.personalfinancialmanagementsystem.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * mybatis
 * 是一个操作数据库的框架 简化JDBC操作
 * BaseMapper<UserDetail>  是 mybatis- plus 提供的一个类
 *  BaseMapper<实体类>  -> 其中提供了一些 基础的 CRUD 接口
 *  实体类 对应数据库中的表名 驼峰 转 蛇形 UD -> u_d
 *  提供了一些条件构造器 用于构造一些简单的sql语句
 *
 */
@Mapper
public interface UserDetailMapper extends BaseMapper<UserDetail> {
}
