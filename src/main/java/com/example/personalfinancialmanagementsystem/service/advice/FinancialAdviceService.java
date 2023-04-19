package com.example.personalfinancialmanagementsystem.service.advice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.personalfinancialmanagementsystem.pojo.advice.FinancialAdvice;

import java.util.List;

/**
 * @author wenlongQAQ
 * @date 2023/4/19 13:13
 */
public interface FinancialAdviceService extends IService<FinancialAdvice> {
    List<FinancialAdvice> getAllAdviceById(String userId,String type);
    Page<FinancialAdvice> getAdvicePage(String userId,Integer page, Integer pageSize);
}
