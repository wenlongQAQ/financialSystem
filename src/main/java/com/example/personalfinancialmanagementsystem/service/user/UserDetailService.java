package com.example.personalfinancialmanagementsystem.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDetail;

/**
 * service 层 服务层
 * mapper 是提供操作数据库的接口 ,而这一层是具体来完成操作的
 * 但是 这个service 只是一个接口 用于规范真正执行操作的类
 */
public interface UserDetailService extends IService<UserDetail> {
}
