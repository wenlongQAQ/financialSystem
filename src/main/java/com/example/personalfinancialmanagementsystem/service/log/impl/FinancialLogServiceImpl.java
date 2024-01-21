package com.example.personalfinancialmanagementsystem.service.log.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.personalfinancialmanagementsystem.mapper.log.FinancialLogMapper;
import com.example.personalfinancialmanagementsystem.pojo.log.FinancialLog;
import com.example.personalfinancialmanagementsystem.pojo.user.UserDetail;
import com.example.personalfinancialmanagementsystem.service.log.FinancialLogService;
import com.example.personalfinancialmanagementsystem.service.user.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialLogServiceImpl extends ServiceImpl<FinancialLogMapper, FinancialLog> implements FinancialLogService {
    @Autowired
    private UserDetailService userDetailService;

    /**
     * 获取理财日志分页信息
     * @param id
     * @param page
     * @param pageSize
     * @param searchTitle
     * @return
     */
    @Override
    public Page<FinancialLog> getPageSelf(String id, Integer page, Integer pageSize, String searchTitle) {
        Page<FinancialLog> logPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<FinancialLog> l = new LambdaQueryWrapper<>();
        l.eq(!"root".equals(id) && !"".equals(id),FinancialLog::getUserId,id)
                .eq("".equals(id),FinancialLog::isVisible,true)
                .like(!"".equals(searchTitle),FinancialLog::getTitle,searchTitle); // 根据名称进行like查找

        Page<FinancialLog> page1 = this.page(logPage, l);
        for (FinancialLog record : page1.getRecords()) {
            LambdaQueryWrapper<UserDetail> ld = new LambdaQueryWrapper<>();
            ld.eq(UserDetail::getUserId,record.getUserId());
            UserDetail tmp = userDetailService.getOne(ld);
            record.setUserName(tmp.getName());
        }
        return page1;
    }
}
