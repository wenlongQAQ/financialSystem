package com.example.personalfinancialmanagementsystem.pojo.order;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;



import java.time.LocalDate;


@Data
public class OrderType {
    private String id;
    private String name;
    private Integer type;
    private String userId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDate saveTime ;

}
