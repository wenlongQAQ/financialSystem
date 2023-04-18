package com.example.personalfinancialmanagementsystem.pojo.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * create table `order`
 * (
 *     order_name    varchar(255) null comment '订单名称',
 *     id            bigint auto_increment
 *         primary key,
 *     type          int          null comment '这条记录是收入 还是支出',
 *     order_num     bigint       null comment '订单号',
 *     order_image   varchar(70)  null comment '订单图片',
 *     order_client  varchar(255) null comment '订单是属于哪个平台的',
 *     save_time     datetime     null comment '订单保存到数据库的时间',
 *     order_time    date         null comment '订单的日期',
 *     order_money   double       null comment '消费/收入 的金额',
 *     order_type_id bigint       null comment '消费的类型，例如 生活消费 电子产品消费',
 *     constraint order_order_num_uindex
 *         unique (order_num)
 * );
 */
@Data
@TableName("orderdetail")
public class Order {
    private String orderName;
    private String id;
    private int type;
    private String orderNum;
    private String orderImage;
    private String comment;
    private LocalDateTime saveTime;
    private Date orderTime;
    private Double orderMoney;
    private String orderTypeId;
    @TableField(exist = false)
    private String orderTypeName;
    private String userId;

}
