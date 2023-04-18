package com.example.personalfinancialmanagementsystem.service.log;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.personalfinancialmanagementsystem.pojo.log.FinancialLog;
import org.springframework.web.bind.annotation.RequestParam;


public interface FinancialLogService extends IService<FinancialLog> {
    Page<FinancialLog>  getPageSelf(String id, Integer page, Integer pageSize, String searchTitle);
}
