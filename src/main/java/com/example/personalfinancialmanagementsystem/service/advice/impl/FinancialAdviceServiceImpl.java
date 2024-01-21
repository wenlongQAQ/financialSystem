package com.example.personalfinancialmanagementsystem.service.advice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.mapper.advice.FinancialAdviceMapper;
import com.example.personalfinancialmanagementsystem.pojo.advice.FinancialAdvice;
import com.example.personalfinancialmanagementsystem.service.advice.FinancialAdviceService;
import com.example.personalfinancialmanagementsystem.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wenlongQAQ
 * @date 2023/4/19 13:13
 */
@Service
public class FinancialAdviceServiceImpl extends ServiceImpl<FinancialAdviceMapper, FinancialAdvice> implements FinancialAdviceService {
    @Autowired
    private UserService userService;

    /**
     * 根据用户ID获取理财建议
     * @param userId
     * @param type
     * @return
     */
    @Override
    public List<FinancialAdvice> getAllAdviceById(String userId, String type) {
        LambdaQueryWrapper<FinancialAdvice> l = new LambdaQueryWrapper<>();
        l.eq(!"root".equals(userId),FinancialAdvice::getUserId,userId)
                .last("recent".equals(type),"limit 3");
        List<FinancialAdvice> advices = this.list(l);

        return advices;
    }

    /**
     * 获取理财建议分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<FinancialAdvice> getAdvicePage(String userId, Integer page, Integer pageSize) {
        Page<FinancialAdvice> advicePage = new Page<>(page,pageSize);
        LambdaQueryWrapper<FinancialAdvice> l = new LambdaQueryWrapper<>();
        l.eq(!"root".equals(userId),FinancialAdvice::getUserId,userId);
        Page<FinancialAdvice> page1 = this.page(advicePage, l);
        List<FinancialAdvice> advices = page1.getRecords();
        for (FinancialAdvice advice : advices) {
            advice.setUserName(userService.getById(advice.getUserId()).getUsername());
        }
        page1.setRecords(advices);
        return page1;
    }


}
