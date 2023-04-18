package com.example.personalfinancialmanagementsystem.service.plan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.mapper.FinancialPlanMapper;
import com.example.personalfinancialmanagementsystem.pojo.plan.FinancialPlan;
import com.example.personalfinancialmanagementsystem.service.plan.FinancialPlanService;
import org.springframework.stereotype.Service;


@Service
public class FinancialPlanServiceImpl extends ServiceImpl<FinancialPlanMapper, FinancialPlan> implements FinancialPlanService {
    @Override
    public Page<FinancialPlan> getPage(Integer page, Integer pageSize, String userId, String searchName) {
        Page<FinancialPlan> planPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<FinancialPlan> l = new LambdaQueryWrapper<>();
        l.eq(FinancialPlan::getUserId,userId)
                .like(!"".equals(searchName),FinancialPlan::getPlanName,searchName);
        return this.page(planPage, l);
    }
}
