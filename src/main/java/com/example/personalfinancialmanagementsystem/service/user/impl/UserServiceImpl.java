package com.example.personalfinancialmanagementsystem.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.mapper.user.UserMapper;
import com.example.personalfinancialmanagementsystem.pojo.order.Order;
import com.example.personalfinancialmanagementsystem.pojo.order.OrderType;
import com.example.personalfinancialmanagementsystem.pojo.user.ChangePasswordDto;
import com.example.personalfinancialmanagementsystem.pojo.user.User;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDetail;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDto;
import com.example.personalfinancialmanagementsystem.service.order.OrderService;
import com.example.personalfinancialmanagementsystem.service.order.OrderTypeService;
import com.example.personalfinancialmanagementsystem.service.user.UserDetailService;
import com.example.personalfinancialmanagementsystem.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderTypeService orderTypeService;
    @Override
    @Transactional
    public void deleteUserAllInformationById(String userId) {
        LambdaQueryWrapper<UserDetail> ld = new LambdaQueryWrapper<>();
        ld.eq(UserDetail::getUserId,userId);
        userDetailService.remove(ld);

        LambdaQueryWrapper<User> lu = new LambdaQueryWrapper<>();
        lu.eq(User::getId,userId);
        this.remove(lu);

        LambdaQueryWrapper<OrderType> orderType = new LambdaQueryWrapper<>();
        orderType.eq(OrderType::getUserId,userId);
        orderTypeService.remove(orderType);

        LambdaQueryWrapper<Order> lo = new LambdaQueryWrapper<>();
        lo.eq(Order::getUserId,userId);
        orderService.remove(lo);
    }

    @Override
    public Page<UserDto> getUserPage(Integer page, Integer pageSize, String name) {
        Page<User> userPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<User> l = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<UserDetail> lUD = new LambdaQueryWrapper<>();
        if (name != null){
            lUD.like(UserDetail::getName,name);
            List<UserDetail> list = userDetailService.list(lUD);
            List<String> idList = list.stream().map(UserDetail::getUserId).collect(Collectors.toList());
            l.in(User::getId,idList);
        }
        l.eq(User::getIdentity,0);
        Page<User> page1 = this.page(userPage, l);
        Page<UserDto> res = new Page<>();
        BeanUtils.copyProperties(page1,res,"records");
        List<UserDto> newRecords = page1.getRecords().stream().map(item -> {
            String userId = item.getId();
            lUD.eq(UserDetail::getUserId,userId);
            UserDetail udById = userDetailService.getOne(lUD);
            item.setUserDetail(udById);
            lUD.clear();
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(item,userDto,"warning");
            if(orderService.getWarningList(userDto.getId()).isEmpty()){
                userDto.setWarning(false);
            }else{
                userDto.setWarning(true);
            }
            return userDto;
        }).collect(Collectors.toList());
        res.setRecords(newRecords);
        return res;
    }

    @Override
    public Integer changePassword(ChangePasswordDto account) {
        LambdaQueryWrapper<User> l = new LambdaQueryWrapper<>();
        l.eq(User::getId,account.getUserId())
                .eq(User::getPassword,account.getOldPassword());
        User one = this.getOne(l);
        if (one == null){
            return Code.QUERY_ERROR;
        }
        one.setPassword(account.getNewPassword());
        this.updateById(one);
        return Code.EDIT_SUCCESS ;
    }
}
