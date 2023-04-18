package com.example.personalfinancialmanagementsystem.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.mapper.user.UserWarningMapper;
import com.example.personalfinancialmanagementsystem.pojo.user.UserWarning;
import com.example.personalfinancialmanagementsystem.service.user.UserWarningService;
import org.springframework.stereotype.Service;

@Service
public class UserWarningServiceImpl extends ServiceImpl<UserWarningMapper, UserWarning> implements UserWarningService {
}
