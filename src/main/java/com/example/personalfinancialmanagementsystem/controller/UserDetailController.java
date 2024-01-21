package com.example.personalfinancialmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.pojo.user.User;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDetail;
import com.example.personalfinancialmanagementsystem.service.user.UserDetailService;
import com.example.personalfinancialmanagementsystem.service.user.UserService;
import com.example.personalfinancialmanagementsystem.service.user.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/uDetail")
@Slf4j
public class UserDetailController {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private UserService userService;


    /**
     * 获取用户详细信息 by Id
     * @param userId
     * @return
     */
    @GetMapping
    public R getUserDetailById(@RequestParam("userId") String userId){
        LambdaQueryWrapper<UserDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserDetail::getUserId,userId);
        UserDetail one =  userDetailService.getOne(lambdaQueryWrapper);
        // TODO
        try {
            int age = UserServiceImpl.getAge(one.getBirth());
            if (age != one.getAge()){
                one.setAge(age);
                userDetailService.saveOrUpdate(one);
            }
        } catch (Exception e) {
            return R.sendMessage("","年龄不合法请联系管理员",Code.QUERY_ERROR);
        }
        User user = userService.getById(userId);
        if (user == null) {
            return R.sendMessage("", "查无此人", Code.QUERY_SUCCESS);
        }
        if (one!=null){
            user.setUserDetail(one);
            return R.sendMessage(user,"", Code.QUERY_SUCCESS);
        }
        UserDetail ud = new UserDetail("",0,userId,"","",0,new Date(System.currentTimeMillis()));
        user.setUserDetail(ud);

        return R.sendMessage(user,"", Code.QUERY_SUCCESS);
    }

    /**
     * 更新用户信息 并且同步更新 用户详情
     * 使用事务控制
     * @param user
     * @return
     */
    @PutMapping
    @Transactional()
    public R saveOrUpdateUser(@RequestBody User user){
        UserDetail userDetail = user.getUserDetail();
        userService.saveOrUpdate(user);
        userDetailService.saveOrUpdate(userDetail);
        return R.sendMessage("","修改成功",Code.EDIT_SUCCESS);
    }


}
