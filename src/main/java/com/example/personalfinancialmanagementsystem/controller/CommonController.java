package com.example.personalfinancialmanagementsystem.controller;

import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.util.RandomCode;
import com.example.personalfinancialmanagementsystem.util.mail.SendMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/common") // 一级路径
@Slf4j
public class CommonController {
    @Value("${filepath}")
    private String path;
    @Autowired
    private SendMail sendMail;

    /**
     * 发送验证码
     * @param username 用户名  账号
     * @param email 和他的邮箱
     * @return
     */
    @GetMapping("/email")
    public R sendMail(@RequestParam("username") String username, @RequestParam("email") String email){
        // 调用 发送 邮件的方法
        String s = sendMail.SendMail(username, email);
        if (s!=null){
            return R.sendMessage(null,"发送成功", Code.SEND_MAIL_SUCCESS);
        }else {
            return R.sendMessage(null,"发送失败",Code.SEND_MAIL_ERROR);
        }
    }


    /**
     * 接收图片
     * @param file
     * @return
     */
    @PostMapping("/uploadImg")
    public R upload(MultipartFile file){
        //file是一个临时文件 不转存 很快消失
        log.info(file.toString());
        //转存
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名,防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                //目录不存在 需要创建
                dir.mkdirs();
            }
            file.transferTo(new File(path+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.sendMessage(fileName,"success",1);
    }

    /**
     * 发送图片
     * 通过图片的名称 获取图片资源 再把图片写入到前端页面上
     * @param name 要下载的图片名称
     * @param response 返回是一个文件流 写入图片到前端页面中
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        //输入流 读取文件内容
        try {
            FileInputStream fileInputStream = new FileInputStream(path + name);
            ServletOutputStream outputStream = response.getOutputStream();
            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
