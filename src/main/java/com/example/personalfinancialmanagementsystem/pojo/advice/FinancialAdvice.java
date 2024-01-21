package com.example.personalfinancialmanagementsystem.pojo.advice;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author wenlongQAQ
 * @date 2023/4/19 13:10
 */
@Data
public class FinancialAdvice {
    private String id;
    private String title;
    private String text;
    private String userId;
    @TableField(exist = false)
    private String userName;
    @TableField(fill = FieldFill.INSERT)
    private LocalDate saveTime;

}
