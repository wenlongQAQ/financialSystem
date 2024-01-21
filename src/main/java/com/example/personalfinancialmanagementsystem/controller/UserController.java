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
        // 条件构造器
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
            //session 相当于会话 相当于两个人聊天 我的名字叫xxx
            // 客户端访问服务器 相当于 一个会话(session) K userId V one.getId()
            //登录成功之后 我们会在session中设置登录的用户的userId
            request.getSession().setAttribute("userId",one.getId());
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
        // 首先 根据用户的账号 获取验证码  HashMap  get
        String s = RandomCode.codeMap.get(user.getUsername());
        // 验证码 不为null  说明他之前请求过验证码 而且验证码没有过期  我们再得到验证码 与 前端提交过来的数据中的验证码进行比较
        if( s != null && s.equals(user.getCode())){
            User user1 = new User();
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            user1.setPhoneNum(user.getPhoneNum());
            user1.setPassword(user.getPassword());
            //账号 密码 邮箱 手机号 封装进User对象 里面
            userService.save(user1);
            // 保存了以后,我们使用的mybatis-plus  user1自动填充里面的id字段
            // 创建一个用户详情信息
            UserDetail ud = new UserDetail();
            // 我们把用户详情信息绑定到用户上
            ud.setUserId(user1.getId());
            // 让他默认的名称 是他的账号
            ud.setName(user1.getUsername());
            //  userDetailService 保存
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

    /**
     * 检验用户名与邮箱是否匹配
     * @param email
     * @param username
     * @return
     */

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
     * @param userId 要退出的这个用户的 ID
     * @param request
     * @return 返回登出信息
     * @RequestParam 代表 这个参数是一个路径参数 包含在URL里面 通常是?后面的部分 userId
     */
    @PutMapping("/loginOut")
    public R loginOut(@RequestParam("userId") String userId,HttpServletRequest request){
        // 获取请求的session 中的字段 userId
        String user = (String) request.getSession().getAttribute("userId");
        // 前端传过来的参数 不为null
        if (userId!=null && userId.equals(user)){
            // 如果一样 我们就把session中的userId属性移除了
            request.getSession().removeAttribute("userId");

            return R.sendMessage("","退出登录成功",Code.EDIT_SUCCESS);

        }else {
            return R.sendMessage("","请检查你的登录状态",Code.EDIT_ERROR);
        }
    }

    /**
     * 用户修改密码
     * @param request
     * @param account
     * @return
     */
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

    /**
     * 管理员分页查询用户
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public R getUserPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "name",required = false) String name){
        Page<UserDto> userPage = userService.getUserPage(page, pageSize, name);
        return R.sendMessage(userPage,"",Code.QUERY_SUCCESS);
    }

    /**
     * 根据ID删除用户
     * @param userId
     * @param request
     * @return
     */

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
