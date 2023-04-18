package com.example.personalfinancialmanagementsystem;

import com.example.personalfinancialmanagementsystem.mapper.order.OrderMapper;
import com.example.personalfinancialmanagementsystem.pojo.user.User;
import com.example.personalfinancialmanagementsystem.service.order.OrderService;
import com.example.personalfinancialmanagementsystem.service.user.UserService;
import com.example.personalfinancialmanagementsystem.util.RandomCode;
import com.example.personalfinancialmanagementsystem.util.mail.SendMail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class PersonalFinancialManagementSystemApplicationTests {
    @Autowired
    private SendMail sendMail;
    @Test
    void contextLoads() {
        sendMail.SendMail("2082841288", RandomCode.generateCode());
        System.out.println(RandomCode.codeMap.get("2082841288"));
    }
    @Autowired
    private UserService userService;
    @Test
    void saveUser(){
        User u = new User();
        userService.save(u);
    }
    @Test
    void test33(){
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(s.format(d));
    }
    @Autowired
    private OrderService orderService;
    @Test
    void testOrderAop(){
        System.out.println(LocalDateTime.now().getMonth());
    }
@Autowired
private OrderMapper orderMapper;
//    @Test
//    void testGetAllMonth(){
//        List result = new ArrayList<>();
//        List<Map<String, Double>> demo = orderMapper.demo();
//        for (Map<String, Double> stringDoubleMap : demo) {
//            Set<Map.Entry<String, Double>> elements = stringDoubleMap.entrySet();
//            ArrayList<Object> objects = new ArrayList<>();
//            for (Map.Entry<String, Double> entry : elements) {
//                System.out.println("key :" +entry.getKey() +"& value : "+entry.getValue());
//
//                if (entry.getKey().equals("month")){
//                    objects.add(entry.getValue()+"月");
//                }else {
//                    objects.add(entry.getValue());
//                }
//            }
//            result.add(objects);
//
//        }
//        System.out.println(result);
//    }
    @Test
    void testGetAllMoneyMonth(){
        System.out.println(new Double(2.01).intValue());

    }
    @Test
    void testDate24(){


    }
}