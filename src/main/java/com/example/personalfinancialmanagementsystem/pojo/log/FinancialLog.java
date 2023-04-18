package com.example.personalfinancialmanagementsystem.pojo.log;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FinancialLog {
    private String id;
    // 文章标题
    private String title;
    // 文章正文
    private String logText;

    private boolean visible;
    //
    @TableField(fill = FieldFill.INSERT)
    private LocalDate saveTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDate updateTime;
    private String userId;
    @TableField(exist = false)
    private String userName;
}
