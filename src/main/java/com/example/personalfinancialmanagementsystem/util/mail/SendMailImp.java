package com.example.personalfinancialmanagementsystem.util.mail;

import com.example.personalfinancialmanagementsystem.util.RandomCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class SendMailImp implements SendMail  {

    private String from ="2082841288@qq.com"; // 发件人

    private String subject ="个人财务管理系统验证码"; // 标题
//

    @Autowired // 自动注入
    private JavaMailSender javaMailSender; // 具体的实现类  spring 自动注入 spring 会帮你管理bean (类) 这个类 已经被spring注入到容器中了
    @Override
    public String SendMail(String username,String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); // 简单邮件 纯文本
        simpleMailMessage.setFrom(from+"(root)"); //发件人
        simpleMailMessage.setTo(email); // 到哪去
        simpleMailMessage.setSubject(subject); // 标题
        // 验证码
        String code = RandomCode.generateCode();
        // 设置 内容 正文
        simpleMailMessage.setText("验证码为："+ code);
        // 发送
        javaMailSender.send(simpleMailMessage);
        // 把验证码 存到系统内存里面 实现类似redis的效果 <用户名:验证码> 当前用户 第一次获取
        if (RandomCode.codeMap.get(username) == null){
            // 内存里面 没有验证码的时候  直接存储
            RandomCode.codeMap.put(username,code);
            new Thread(()->{
                try {
                    // 睡眠 三分钟 三分钟以后 如果验证码 还未被使用 直接使验证码失效
                    Thread.sleep(60000 * 3);
                    if (RandomCode.codeMap.get(username) != null) {
                        RandomCode.codeMap.remove(username);
                        log.warn("remove"+ username +"code :" + code);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }else {
            // 移除之前的验证码
            RandomCode.codeMap.remove(username);
            // 添加新的验证码
            RandomCode.codeMap.put(username,code);
        }

        return code; //返回验证码


    }

}