package com.example.personalfinancialmanagementsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.personalfinancialmanagementsystem.common.Code;
import com.example.personalfinancialmanagementsystem.common.R;
import com.example.personalfinancialmanagementsystem.pojo.log.FinancialLog;
import com.example.personalfinancialmanagementsystem.service.log.FinancialLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/financial")
public class FinancialController {
    @Autowired
    private FinancialLogService financialLogService;

    /**
     * 新增日志
     * @param newLog
     * @return
     */
    @PostMapping()
    public R addLog(@RequestBody FinancialLog newLog){
        financialLogService.save(newLog);
        return R.sendMessage("","新增成功", Code.EDIT_SUCCESS);
    }

    /**
     * 根据ID获取日志,用于修改日志内容
     * @param id
     * @return
     */
    @GetMapping
    public R getOneLog(@RequestParam("id") String id){
        FinancialLog one = financialLogService.getById(id);
        return R.sendMessage(one,"",Code.QUERY_SUCCESS);
    }

    /**
     * 分页查询日志
     * @param id
     * @param page
     * @param pageSize
     * @param searchTitle
     * @return
     */
    @GetMapping("/pageSelf")
    public R getPageSelf(@RequestParam(value = "id",required = false) String id, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "searchName",required = false) String searchTitle){
        Page<FinancialLog> pageSelf = financialLogService.getPageSelf(id, page, pageSize, searchTitle);
        return R.sendMessage(pageSelf,"",Code.QUERY_SUCCESS);
    }

    /**
     * 修改日志
     * @param log
     * @return
     */
    @PutMapping
    public R editLog(@RequestBody FinancialLog log){
        financialLogService.updateById(log);
        return R.sendMessage("","修改成功",Code.EDIT_SUCCESS);
    }

    /**
     * 删除日志
     * @param id
     * @return
     */
    @DeleteMapping
    public R deleteLog(@RequestParam String id){
        financialLogService.removeById(id);
        return R.sendMessage("","删除成功",Code.EDIT_SUCCESS);
    }

}
