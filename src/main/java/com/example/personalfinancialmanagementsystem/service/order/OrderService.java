package com.example.personalfinancialmanagementsystem.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.personalfinancialmanagementsystem.pojo.order.DayOrder;
import com.example.personalfinancialmanagementsystem.pojo.order.Order;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderService extends IService<Order> {
    public List<List> getAllMonthMoney(Integer type, String userId,Date begin, Date end);
    public List<HashMap<String,String>> getMoneyAndTypeMap(String userId, Integer type,Date begin, Date end);
    public Integer countDangerOrder();
    List<String> getWarningList(String userId);
    DayOrder getDayIncome(String type);

    List<HashMap<String,String>> getMoneyAndTypeMapByDate(String userId, String type, Date begin, Date end);
    List<Order> getDayOrderDetail(String type, java.sql.Date begin, java.sql.Date end);
}
