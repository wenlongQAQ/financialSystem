package com.example.personalfinancialmanagementsystem.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.mapper.order.OrderMapper;
import com.example.personalfinancialmanagementsystem.pojo.order.DayOrder;
import com.example.personalfinancialmanagementsystem.pojo.order.Order;
import com.example.personalfinancialmanagementsystem.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 将 map 返回 如同[[月份,金额],[月份,金额]]的形式
     *
     * @param type
     * @param userId
     * @return
     */
    @Override
    public List<List> getAllMonthMoney(Integer type, String userId, java.sql.Date begin, java.sql.Date end) {
        List<List> result = new ArrayList<>();
        List<Map> allMonthMoney = orderMapper.getAllMonthMoney(type,userId,begin,end);
        Integer position = 1;
        // 先确定好 1-12个月每个月份
        for (int i = 1; i <= 12; i++){
            ArrayList<String> demo = new ArrayList<>();
            demo.add(i+"月");
            demo.add(0.0+"");
            result.add(demo);
        }
        // 得到的数据是 [month-> ?  sum -?]的格式
        for (Map stringDoubleMap : allMonthMoney) {
            Set<Map.Entry> elements = stringDoubleMap.entrySet();

            //遍历Map集合
            for (Map.Entry entry : elements) {
                // 如果此时是 month -> ? 的数据 就将 position 位置表示 设置为 当前月份 -1 因为 ArrayList 下标从0 开始
                if (entry.getKey().equals("month")){
                    position = (Integer) entry.getValue();
                }else {
                    // 如果此时是 sum -> ? 的数据 就将 数据插入到position的位置 就将月份与收入总和对应上了
                    result.get(position-1).set(1, entry.getValue());
                }
            }
        }
        return result;
    }
    @Override
    public List<HashMap<String, String>> getMoneyAndTypeMap(String userId, Integer type, java.sql.Date begin, java.sql.Date end) {
        return orderMapper.getMoneyAndTypeMap(userId, type,begin,end);
    }

    @Override
    public Integer countDangerOrder() {
        LambdaQueryWrapper<Order> l = new LambdaQueryWrapper<>();
        java.util.Date date = new java.util.Date();
        // 得到从现在开始 24小时前的时刻, 由于保存到数据库中的时间 是该条数据日期的00 : 00 所以 提前24h 并不会读取到昨日的数据
        long yesterday = Long.valueOf(date.getTime()) - 60 * 60 * 24 * 1000;
        //得到从现在开始 24小时候的数据
        long today = Long.valueOf(date.getTime()) + 60 * 60 * 24 * 1000;
        l.between(Order::getOrderTime,new java.util.Date(yesterday),new java.util.Date(today))
                .ge(Order::getOrderMoney,10000);
        return super.count(l);
    }

    @Override
    public List<String> getWarningList(String userId) {
//        List<List> incomes = this.getAllMonthMoney(1, userId);
//        List<List> expends = this.getAllMonthMoney(0, userId);
//        List<String > warningList = new ArrayList<>();
//        for (List expend : expends) {
//            String expendMonth = (String) expend.get(0);
//            Double expendMoney = Double.valueOf(expend.get(1).toString());
//            for (List income : incomes) {
//                String incomeMonth = (String) income.get(0);
//                Double incomeMoney = Double.valueOf(income.get(1).toString());
//                if (incomeMonth.equals(expendMonth) && incomeMoney < expendMoney){
//                    warningList.add(expendMonth);
//                    break;
//                }
//            }
//        }
        return null;
    }

    @Override
    public DayOrder getDayIncome(String type) {
        DayOrder dayIncome = orderMapper.getDayIncome(type);
        if (dayIncome.getAmount() == null) {
            dayIncome.setAmount(0.0);
        }
        return dayIncome;
    }

    @Override
    public List<HashMap<String, String>> getMoneyAndTypeMapByDate(String userId, String type, java.sql.Date begin, java.sql.Date end) {
        return orderMapper.getMoneyAndTypeMapByDate(userId, type, begin, end);
    }

    @Override
    public List<Order> getDayOrderDetail(String type, java.sql.Date begin, java.sql.Date end) {
        return orderMapper.getDayOrderDetail(type, begin, end);
    }


}
