package com.hyles.shuimen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @SpringBootApplication: Spring Boot 应用程序的入口类,将会启用 Spring Boot 的自动配置和组件扫描
 * @Slf4j: 在编译时自动生成一个 log 字段，可以用来进行日志记录
 * @EnableTransactionManagement 开启事务方法
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ShuiMenApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShuiMenApplication.class);
        log.info("项目启动成功！！！");
    }
}
