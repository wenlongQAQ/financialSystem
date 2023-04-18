package com.example.personalfinancialmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TransactionMangerBean {
    @Bean
    public DataSourceTransactionManager transactionManger(DataSource dataSource){
        DataSourceTransactionManager transactionManger = new DataSourceTransactionManager();
        transactionManger.setDataSource(dataSource);
        return transactionManger;
    }
}