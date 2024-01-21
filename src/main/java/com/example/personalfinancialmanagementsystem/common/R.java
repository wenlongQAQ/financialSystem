package com.example.personalfinancialmanagementsystem.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    // 数据
    private Object data;
    // 信息 我们想传达给前端的信息 是否成功 为啥失败
    private String msg;
    // 返回的代码 错误 / 成功 代码
    private Integer code;

    public  static R sendMessage(Object data, String msg, Integer code){
        R r = new R();
        r.setData(data);
        r.setMsg(msg);
        r.setCode(code);
        return r;
    }
}
