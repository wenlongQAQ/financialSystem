package com.example.personalfinancialmanagementsystem.service.plan;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.personalfinancialmanagementsystem.pojo.plan.FinancialPlan;


public interface FinancialPlanService extends IService<FinancialPlan> {
    Page<FinancialPlan> getPage(Integer page, Integer pageSize, String userId, String searchName);
}
