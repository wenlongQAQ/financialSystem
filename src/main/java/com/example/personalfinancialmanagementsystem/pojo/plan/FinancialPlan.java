package com.example.personalfinancialmanagementsystem.pojo.plan;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Date;

@Data
public class FinancialPlan {
    private String userId;
    private String Id;
    private String planName;
    private Double planMoney;
    private String detail;
    private String comment;

    private Date saveTime;
    private Date endTime;



}
