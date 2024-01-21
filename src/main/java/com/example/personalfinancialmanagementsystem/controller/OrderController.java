package com.example.personalfinancialmanagementsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.pojo.order.Order;
import com.example.personalfinancialmanagementsystem.service.order.OrderService;
import com.example.personalfinancialmanagementsystem.service.order.OrderTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/order")
@Slf4j
// controller 的作用 用于实现前后端的数据交互
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderTypeService orderTypeService;
    /**
     * 分页查询 收入/支出信息
     * @param type 收入/支出
     * @param page 页码
     * @param pageSize 页大小
     * @param dates 日期查询条件
     * @return 返回查询到的页
     */
    @GetMapping("/page")
    public R getList(@RequestParam("type") Integer type,
                     @RequestParam("page") Integer page,
                     @RequestParam("pageSize") Integer pageSize,
                     @RequestParam(value = "dates",required = false) List<Long> dates,
                     HttpServletRequest request
    ){
        Page<Order> orderPage = new Page<>(page,pageSize); //构造一个Order的页对象
        LambdaQueryWrapper<Order> l = new LambdaQueryWrapper<>(); //构造一个条件包装器
        String userId = (String)request.getSession().getAttribute("userId");
        if (userId==null)
            return R.sendMessage("","请您先登录",Code.QUERY_ERROR);
        l.eq(Order::getType,type).eq(Order::getUserId,userId);
        if (dates != null){
            Date d1 = new Date(dates.get(0));
            Date d2 = new Date(dates.get(1));
            log.info(d1.toString());
            l.between(Order::getOrderTime,d1,d2);
        }
        Page<Order> page1 = orderService.page(orderPage,l);
        List<Order> collect = page1.getRecords().stream().map(item -> {
            if (item.getOrderTypeId() == null){
                item.setOrderTypeId(item.getType() == 1 ? Code.DEFAULT_ORDER_INCOME_TYPE_ID:Code.DEFAULT_ORDER_EXPEND_TYPE_ID);
                orderService.updateById(item);
            }
            item.setOrderTypeName(orderTypeService.getById(item.getOrderTypeId()).getName());
            return item;
        }).collect(Collectors.toList());
        page1.setRecords(collect);
        return R.sendMessage(page1,"", Code.QUERY_SUCCESS);
    }

    /**
     * 修改/新增收支
     * @param order 需要修改的收支信息
     * @return 返回操作代码
     */
    @PutMapping
    public R saveOrUpdate(@RequestBody Order order,HttpServletRequest request){
        order.setUserId(request.getSession().getAttribute("userId").toString());
        if (order.getOrderTypeId() == null || order.getOrderTypeId().equals("") ){
            order.setOrderTypeId(order.getType() == 1 ?Code.DEFAULT_ORDER_INCOME_TYPE_ID:Code.DEFAULT_ORDER_EXPEND_TYPE_ID);
        }
        order.setOrderNum(String.valueOf(System.currentTimeMillis()));
        boolean b = orderService.saveOrUpdate(order);
        Integer count = orderService.countDangerOrder();

        if (order.getType() == 0 && order.getOrderMoney() > 10000 &&  count > 3 ){
            return R.sendMessage("","小心 你今天已经有" +  count + "次大额消费了,请注意哦",Code.ORDER_WARNING);
        }
        return R.sendMessage("",b?"成功":"出现异常，请重试",b?Code.EDIT_SUCCESS:Code.EDIT_ERROR);
    }


    /**
     * 通过id获得收支对象
     * @param id 收支的Id
     * @return 返回对应的收支
     */
    @GetMapping("/getById")
    public R getOrderById(@RequestParam("id") String id){
        Order byId = orderService.getById(id);
        byId.setOrderTypeName(orderTypeService.getById(byId.getOrderTypeId()).getName());
        return R.sendMessage(byId,"",Code.QUERY_SUCCESS);
    }

    /**
     * 批量删除收支
     * @param ids 需要删除的收支id
     * @return 返回操作代码(成功 / 失败)
     */
    @DeleteMapping
    public R deleteOrder(@RequestParam List<String> ids ){
        orderService.removeByIds(ids);
        return R.sendMessage(null,"删除成功",Code.EDIT_SUCCESS);
    }

    /**
     * 得到今年每个月的收支情况
     * @param type 1 收入 0 支出
     * @param userId 需要查询的用户的Id
     * @return 返回每个月的收支情况
     */
    @GetMapping("/moneyList")
    public R getAllMonthMoney(@RequestParam("type") Integer type,@RequestParam("userId") String userId,@RequestParam("begin") Long begin, @RequestParam("end") Long end){

        List<List> allMonthMoney = orderService.getAllMonthMoney(type,userId,new Date(begin),new Date(end));
        return R.sendMessage(allMonthMoney,"",Code.QUERY_SUCCESS);
    }

    /**
     * 返回前端数据 形如[{"类型","金额"},{"类型","金额"}]的格式 以供饼图使用
     * @param type 1 收入 0 支出
     * @param userId 需要查询的用户的Id
     * @param request 用来获取当前登录的用户Id
     * @return 返回查询到的数据 Map集合
     */
    @GetMapping("/getMap")
    public R getMoneyAndTypeMap(@RequestParam("type") Integer type,@RequestParam("userId") String userId,HttpServletRequest request,@RequestParam("begin") Long begin, @RequestParam("end") Long end){
        String userId1 = (String) request.getSession().getAttribute("userId");
        if (userId1 == null) {
            return R.sendMessage("","请确认你的登录",Code.QUERY_ERROR);
        }
        List<HashMap<String, String>> data = orderService.getMoneyAndTypeMap(userId, type,new Date(begin),new Date(end));
        return R.sendMessage(data,"",Code.QUERY_SUCCESS);
    }


}
