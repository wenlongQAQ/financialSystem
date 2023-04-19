package com.example.personalfinancialmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.pojo.advice.FinancialAdvice;
import com.example.personalfinancialmanagementsystem.service.advice.FinancialAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wenlongQAQ
 * @date 2023/4/19 13:18
 */
@RestController
@RequestMapping("/advice")
public class AdviceController {
    @Autowired
    private FinancialAdviceService financialAdviceService;

    @PutMapping
    public R addOrUpdateAdvice(@RequestBody FinancialAdvice advice){
        financialAdviceService.saveOrUpdate(advice);
        return R.sendMessage("","", Code.EDIT_SUCCESS);
    }
    @GetMapping("/getOne")
    public R getOneAdvice(@RequestParam("id") String id){
        FinancialAdvice oneAdvice = financialAdviceService.getById(id);
        return R.sendMessage(oneAdvice,"",Code.QUERY_SUCCESS);
    }
    @GetMapping
    public R getAllAdviceById(@RequestParam("userId") String userId,@RequestParam("type") String type){
        List<FinancialAdvice> allAdviceById = financialAdviceService.getAllAdviceById(userId,type);
        return R.sendMessage(allAdviceById,"",Code.QUERY_SUCCESS);
    }
    @GetMapping("/page")
    public R getAllAdvicePage(@RequestParam("userId") String userId,@RequestParam("page") Integer page, Integer pageSize){
        Page<FinancialAdvice> advicePage = financialAdviceService.getAdvicePage(userId, page, pageSize);
        return R.sendMessage(advicePage,"",Code.QUERY_SUCCESS);
    }

    @DeleteMapping
    public R deleteById(@RequestParam("ids") List<String> ids){
        financialAdviceService.removeByIds(ids);
        return R.sendMessage("","",Code.EDIT_SUCCESS);
    }
}
