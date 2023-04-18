package com.example.personalfinancialmanagementsystem.service.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.mapper.order.OrderTypeMapper;
import com.example.personalfinancialmanagementsystem.pojo.order.OrderType;
import com.example.personalfinancialmanagementsystem.service.order.OrderTypeService;
import org.springframework.stereotype.Service;

@Service
public class OrderTypeServiceImpl extends ServiceImpl<OrderTypeMapper, OrderType> implements OrderTypeService {
}
