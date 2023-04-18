package com.example.personalfinancialmanagementsystem.pojo.user;

import lombok.Getter;
import lombok.Setter;

@Getter //生成get方法
@Setter // set方法
public class UserWarning {
    private String userId;
    private String id;
    private Double warningMoney;
    private Integer warningMonth;
}
