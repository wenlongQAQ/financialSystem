package com.example.personalfinancialmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.pojo.order.Order;
import com.example.personalfinancialmanagementsystem.pojo.order.OrderType;
import com.example.personalfinancialmanagementsystem.service.order.OrderService;
import com.example.personalfinancialmanagementsystem.service.order.OrderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/orderType")
public class OrderTypeController {
    @Autowired
    private OrderTypeService orderTypeService;
    @Autowired
    private OrderService orderService;

    /**
     * 根据类型获取订单种类
     * @param type 类型 收入/支出
     * @return
     */
    @GetMapping
    public R getOrderType(@RequestParam("type") Integer type, HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        LambdaQueryWrapper<OrderType> l = new LambdaQueryWrapper<>();
        l.eq(OrderType::getUserId,userId)
                .eq(OrderType::getType,type);
        List<OrderType> list = orderTypeService.list(l);
        OrderType orderType = new OrderType();
        orderType.setType(type);
        orderType.setId(type == 1? Code.DEFAULT_ORDER_INCOME_TYPE_ID : Code.DEFAULT_ORDER_EXPEND_TYPE_ID);
        orderType.setName(type == 1 ? "默认收入" : "默认支出");
        list.add(orderType);
        return R.sendMessage(list,"", Code.QUERY_SUCCESS);
    }

    @GetMapping("/getById")
    public R getOrderTypeById(@RequestParam("id") String id){
        OrderType byId = orderTypeService.getById(id);
        return R.sendMessage(byId,"",Code.QUERY_SUCCESS);
    }

    @DeleteMapping
    @Transactional
    public R deleteByIds(@RequestParam("ids") List<String > ids){
        LambdaQueryWrapper<Order> l = new LambdaQueryWrapper<>();
        l.in(Order::getOrderTypeId,ids);
        List<Order> orders = orderService.list(l);
        if (orders!=null){
            for (Order order : orders) {
                if (order.getType() == 0)
                    order.setOrderTypeId(Code.DEFAULT_ORDER_EXPEND_TYPE_ID);
                else
                    order.setOrderTypeId(Code.DEFAULT_ORDER_INCOME_TYPE_ID);
            }
            orderService.updateBatchById(orders);
        }
        orderTypeService.removeByIds(ids);
        return R.sendMessage("","",Code.EDIT_SUCCESS);
    }


    @GetMapping("/page")
    public R getPage(@RequestParam("page") Integer page,
                     @RequestParam("pageSize") Integer pageSize,
                     @RequestParam(value = "name",required = false) String name,
                     @RequestParam(value = "searchType",required = false) Integer type,
                     HttpServletRequest request ){
        Page<OrderType> orderTypePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<OrderType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(OrderType::getUserId,request.getSession().getAttribute("userId").toString())
                .eq(!name.equals(""),OrderType::getName,name)
                .eq(type!=2,OrderType::getType,type);
        Page<OrderType> page1 = orderTypeService.page(orderTypePage, lambdaQueryWrapper);
        return R.sendMessage(page1,"",Code.QUERY_SUCCESS);
    }

    @PutMapping
    public R addOrderType(@RequestParam("userId") String userId,@RequestBody OrderType orderType,HttpServletRequest request){
        if (userId!=null && userId.equals(request.getSession().getAttribute("userId"))){
            LambdaQueryWrapper<OrderType> l = new LambdaQueryWrapper<>();
            l.eq(OrderType::getUserId,userId)
                    .eq(OrderType::getName,orderType.getName());
            if (orderTypeService.getOne(l) != null) {
                return R.sendMessage("","类型名称:" + orderType.getName() +  "已存在",Code.EDIT_ERROR);
            }
            orderType.setUserId(userId);
            orderTypeService.saveOrUpdate(orderType);
            return R.sendMessage("","操作成功",Code.EDIT_SUCCESS);

        }else {
            return R.sendMessage("","请确认你的登录状态",Code.EDIT_ERROR);
        }
    }

}
