package com.example.personalfinancialmanagementsystem.controller;


import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.pojo.order.DayOrder;
import com.example.personalfinancialmanagementsystem.pojo.order.Order;
import com.example.personalfinancialmanagementsystem.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dayOrder")
public class DayOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 获取今日的收入总单数和总金额
     * @param type
     * @return
     */
    @GetMapping("/today")
    public DayOrder getDayOrderSum(HttpServletRequest request,@RequestParam("type") String type){
        return orderService.getDayIncome((String) request.getSession().getAttribute("userId"),type);
    }
    @GetMapping("/getMap")
    public R getMoneyTypeMap(@RequestParam("userId") String userId, @RequestParam("type") String type, @RequestParam("begin") Long begin, @RequestParam("end") Long end){
        Date beginDate = new Date(begin);
        Date endDate = new Date(end);
        List<HashMap<String, String>> moneyAndTypeMap = orderService.getMoneyAndTypeMapByDate(userId, type, beginDate, endDate);
        return R.sendMessage(moneyAndTypeMap,"查询成功", Code.QUERY_SUCCESS);
    }
    @GetMapping("/todayOrder")
    public R getToDayOrderDetail(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("begin") Long begin, @RequestParam("end") Long end){

        List<Order> res = orderService.getDayOrderDetail((String) request.getSession().getAttribute("userId"),type,new Date(begin),new Date(end));
        return R.sendMessage(res,"查询成功",Code.QUERY_SUCCESS);


    }


}
