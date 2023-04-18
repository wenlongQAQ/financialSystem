package com.example.personalfinancialmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;


import com.example.personalfinancialmanagementsystem.pojo.user.*;
import com.example.personalfinancialmanagementsystem.service.user.UserDetailService;
import com.example.personalfinancialmanagementsystem.service.user.UserService;
import com.example.personalfinancialmanagementsystem.util.RandomCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService userDetailService;
    /**
     * 用户登录
     * @param account
     * @return
     */
    @PostMapping("/logon")
    public R userLogon(@RequestBody User account, HttpServletRequest request){
        LambdaQueryWrapper<User> l = new LambdaQueryWrapper<>();
        l.eq(User::getUsername,account.getUsername())
                .eq(User::getPassword,account.getPassword())
                .eq(User::getIdentity,account.getIdentity());
        User one = userService.getOne(l);
        if (one != null){
            LambdaQueryWrapper<UserDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserDetail::getUserId,one.getId());
            UserDetail ud =  userDetailService.getOne(lambdaQueryWrapper);
            if (ud!=null){
                one.setUserDetail(ud);
            }
            request.getSession().setAttribute("userId",one.getId());
            log.info((String) request.getSession().getAttribute("userId"));
            return R.sendMessage(one,"登录成功",Code.LOGIN_SUCCESS);
        }else {
            return R.sendMessage("","账号或密码错误",Code.LOGIN_ERROR);
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @Transactional
    public R userRegister(@RequestBody UserRegister user){
        String s = RandomCode.codeMap.get(user.getUsername());
        if(s != null &&RandomCode.codeMap.get(user.getUsername()).equals(user.getCode())){
            User user1 = new User();
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            user1.setPhoneNum(user.getPhoneNum());
            user1.setPassword(user.getPassword());
            UserDetail ud = new UserDetail();
            userService.save(user1);
            LambdaQueryWrapper<User> l = new LambdaQueryWrapper<>();
            l.eq(User::getUsername,user.getUsername());
            User one = userService.getOne(l);

            ud.setUserId(one.getId());
            ud.setName(user.getUsername());
            userDetailService.save(ud);
            RandomCode.codeMap.remove(user.getUsername(),user.getCode());
            return R.sendMessage("","注册成功",Code.REGISTER_SUCCESS);
        }else {
            return R.sendMessage("","验证码错误/当前账号与获取验证码前账号不匹配",Code.REGISTER_ERROR);
        }
    }

    /**
     * 用户找回密码
     * @param user 需要找回密码的用户信息
     * @return 返回操作状态信息
     */
    @PutMapping("/forgetPassword")
    public R forgetPassword(@RequestBody UserRegister user){
        String s = RandomCode.codeMap.get(user.getUsername());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User one = userService.getOne(lambdaQueryWrapper);
        // TODO 修改 if-else结构
        if (one != null){
            if (user.getEmail().equals(one.getEmail())){
                if(user.getCode().equals(RandomCode.codeMap.get(user.getUsername()))){
                    one.setPassword(user.getPassword());
                    userService.updateById(one);
                    RandomCode.codeMap.remove(user.getUsername());
                }else return R.sendMessage("","验证码错误/过期",Code.REGISTER_ERROR);
            }else return  R.sendMessage("","邮箱不匹配",Code.REGISTER_ERROR);
        }else return R.sendMessage("","账号不存在",Code.REGISTER_ERROR);
        return R.sendMessage("","修改成功",Code.REGISTER_SUCCESS);
    }

    @GetMapping("/email")
    public R getUserEmail(@RequestParam("email") String email,@RequestParam("username") String username){
        LambdaQueryWrapper<User> l = new LambdaQueryWrapper<>();
        l.eq(User::getUsername,username);
        User one = userService.getOne(l);
        if (email.equals(one.getEmail())) {
            return R.sendMessage(true,"",Code.QUERY_SUCCESS);
        }else{
            return R.sendMessage(false,"账号与邮箱不匹配",Code.QUERY_SUCCESS);
        }
    }
    /**
     * 用户登出
     * @param userId
     * @param request
     * @return 返回登出信息
     */
    @PutMapping("/loginOut")
    public R loginOut(@RequestParam("userId") String userId,HttpServletRequest request){
        String user = (String) request.getSession().getAttribute("userId");
        if (userId!=null && userId.equals(user)){
            request.getSession().removeAttribute("userId");
            return R.sendMessage("","退出登录成功",Code.EDIT_SUCCESS);
        }else {
            return R.sendMessage("","请检查你的登录状态",Code.EDIT_ERROR);
        }
    }

    @PutMapping("/editPassword")
    public R changePassword(HttpServletRequest request, @RequestBody ChangePasswordDto account){
        String userId = (String) request.getSession().getAttribute("userId");

        if (userId == null)
            return R.sendMessage("","请检查你的登录状态",Code.USER_LOGIN_OUT);
        account.setUserId(userId);
        Integer flag = userService.changePassword(account);
        switch (flag){
            case Code.QUERY_ERROR: return R.sendMessage("","当前登录账号与旧密码不匹配",Code.QUERY_ERROR);
            case Code.EDIT_SUCCESS: return R.sendMessage("","修改成功",Code.EDIT_SUCCESS);
        }
        return R.sendMessage("","未知异常",Code.UNKNOWN_EXCEPTION);

    }

    @GetMapping("/page")
    public R getUserPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "name",required = false) String name){
        Page<UserDto> userPage = userService.getUserPage(page, pageSize, name);
        return R.sendMessage(userPage,"",Code.QUERY_SUCCESS);
    }
    @DeleteMapping

    public R deleteUserById(@RequestParam("userId") String userId,HttpServletRequest request){
        String rootId = (String) request.getSession().getAttribute("userId");
        if(rootId == null){
            return R.sendMessage("","请检查你的登录状态",Code.USER_LOGIN_OUT);
        }
        userService.deleteUserAllInformationById(userId);
        return R.sendMessage("","删除成功!",Code.EDIT_SUCCESS);
    }
}
