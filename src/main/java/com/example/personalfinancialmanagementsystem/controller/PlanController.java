package com.example.personalfinancialmanagementsystem.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.pojo.plan.FinancialPlan;
import com.example.personalfinancialmanagementsystem.service.plan.FinancialPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;

@RestController
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private FinancialPlanService financialPlanService;

    @GetMapping("/page")
    public R getPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("id") String userId,@RequestParam(value = "searchName",required = false) String searchName){
        Page<FinancialPlan> page1 = financialPlanService.getPage(page, pageSize, userId, searchName);
        return R.sendMessage(page1,"select success", Code.QUERY_SUCCESS);
    }
    @DeleteMapping
    public R deletePlanBatch(@RequestParam  ArrayList<String> ids){
        financialPlanService.removeByIds(ids);
        return R.sendMessage("","",Code.EDIT_SUCCESS);
    }
    @GetMapping("/getOne")
    public R getById(@RequestParam("id") String id){
        return R.sendMessage(financialPlanService.getById(id),"",Code.QUERY_SUCCESS);
    }
    @PutMapping
    public R editUser(@RequestBody FinancialPlan financialPlan){
        financialPlan.setSaveTime(new Date(System.currentTimeMillis()));
        financialPlanService.saveOrUpdate(financialPlan);
        return R.sendMessage("","",Code.EDIT_SUCCESS);
    }

}
