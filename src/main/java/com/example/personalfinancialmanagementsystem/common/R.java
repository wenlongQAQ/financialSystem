package com.example.personalfinancialmanagementsystem.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private Object data;
    private String msg;
    private Integer code;

    public  static R sendMessage(Object data, String msg, Integer code){
        R r = new R();
        r.setData(data);
        r.setMsg(msg);
        r.setCode(code);
        return r;
    }
}
