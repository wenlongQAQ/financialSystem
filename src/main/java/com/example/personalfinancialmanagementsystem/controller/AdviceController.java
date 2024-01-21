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

    /**
     * 新增或者修改一条理财建议
     * @param advice
     * @return
     */
    @PutMapping
    public R addOrUpdateAdvice(@RequestBody FinancialAdvice advice){
        financialAdviceService.saveOrUpdate(advice);
        return R.sendMessage("","", Code.EDIT_SUCCESS);
    }

    /**
     * 根据ID获取一条理财建议,用户端使用
     * @param id
     * @return
     */
    @GetMapping("/getOne")
    public R getOneAdvice(@RequestParam("id") String id){
        FinancialAdvice oneAdvice = financialAdviceService.getById(id);
        return R.sendMessage(oneAdvice,"",Code.QUERY_SUCCESS);
    }

    /**
     * 获取管理员给用户发送的理财建议
     * @param userId
     * @param type
     * @return
     */
    @GetMapping
    public R getAllAdviceById(@RequestParam("userId") String userId,@RequestParam("type") String type){
        List<FinancialAdvice> allAdviceById = financialAdviceService.getAllAdviceById(userId,type);
        return R.sendMessage(allAdviceById,"",Code.QUERY_SUCCESS);
    }

    /**
     * 分页查询理财建议信息
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R getAllAdvicePage(@RequestParam("userId") String userId,@RequestParam("page") Integer page, Integer pageSize){
        Page<FinancialAdvice> advicePage = financialAdviceService.getAdvicePage(userId, page, pageSize);
        return R.sendMessage(advicePage,"",Code.QUERY_SUCCESS);
    }

    /**
     * 根据ids批量删除理财建议
     * @param ids
     * @return
     */

    @DeleteMapping
    public R deleteById(@RequestParam("ids") List<String> ids){
        financialAdviceService.removeByIds(ids);
        return R.sendMessage("","",Code.EDIT_SUCCESS);
    }
}
