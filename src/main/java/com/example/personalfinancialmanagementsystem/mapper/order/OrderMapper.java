package com.example.personalfinancialmanagementsystem.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.personalfinancialmanagementsystem.pojo.order.DayOrder;
import com.example.personalfinancialmanagementsystem.pojo.order.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
//    @Select("select sum(order_money) from orderdetail where type = #{type} and user_id = #{userId} group by  month(order_time) order by month(order_time) asc;")
//    public List<Double> getAllMonthMoney(Integer type,String userId);

    /**
     * 填充饼图信息,获取每种收支的金额总数
     * @param userId
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Select("select SUM(od.order_money) as value,ot.name as name\n" +
            "from orderdetail od , order_type ot  where od.user_id = #{userId} and od.type = #{type} and  od.order_type_id = ot.id and od.order_time between #{begin} and #{end} group by ot.name order by od.order_money desc")
    public List<HashMap<String,String>> getMoneyAndTypeMap(String userId, Integer type, java.sql.Date begin, java.sql.Date end);

    /**
     * 获取折线图数据,获取所有月份收支总数
      * @param type
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    @Select("select sum(order_money) sum,month(order_time) month from orderdetail where type = #{type} and user_id = #{userId} and order_time between #{begin} and #{end}  group by month(order_time) order by month(order_time) asc")
    public List<Map> getAllMonthMoney(Integer type, String userId, java.sql.Date begin, java.sql.Date end);

    /**
     * 用于收入日报
     * @param userId
     * @param type
     * @return
     */
    @Select("select count(1) count,sum(order_money) amount from orderdetail where user_id = #{userId} and day(order_time) = day(now()) and year(order_time) = year(now()) and month(order_time) = month(now()) and type=#{type};")
    public DayOrder getDayIncome(String userId,String type);

    /**
     * 用于收支日报详情信息,例如消费的订单数,和消费的种类
     * @param userId
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Select("select * from orderdetail od where od.user_id = #{userId} " +
            "and od.type = #{type} " +
            "and year(od.order_time) between year(#{begin}) and year(#{end})" +
            "and month(od.order_time) between month(#{begin}) and month(#{end}) " +
            "and day(od.order_time) between day(#{begin}) and day(#{end})"
    )
    List<Order> getDayOrderDetail(String userId,String type, java.sql.Date begin, java.sql.Date end);

    /**
     * 用于饼图
     * @param userId
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Select("select SUM(od.order_money) as value,ot.name as name\n" +
            "from orderdetail od , order_type ot  where od.user_id = #{userId} " +
            "and od.type = #{type} " +
            "and  od.order_type_id = ot.id  " +
            "and year(od.order_time) between year(#{begin}) and year(#{end})" +
            "and month(od.order_time) between month(#{begin}) and month(#{end}) " +
            "and day(od.order_time) between day(#{begin}) and day(#{end})" +
            " group by ot.name order by od.order_money desc")
    List<HashMap<String,String>> getMoneyAndTypeMapByDate(String userId, String type, Date begin, Date end);




}
