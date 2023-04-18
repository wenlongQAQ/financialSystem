package com.example.personalfinancialmanagementsystem.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody //拦截异常后返回R对象转换为json数据
@Slf4j
public class GlobalExceptionHandler {
    private static Map<String,String> exceptionType = new HashMap<>();
    static {
        exceptionType.put("'user.user_username_uindex'","用户名：");
    }
    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        String msg="";
        if (ex.getMessage().contains("Duplicate entry")){
            String[] s = ex.getMessage().split(" ");
                msg += exceptionType.get(s[5]) + s[2] + "已存在!";

            return R.sendMessage("",msg,Code.REPEAT_ERROR);

        }
        else {
            return R.sendMessage("","未知异常",Code.REPEAT_ERROR);
        }
    }


}