package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel(){
        // 请求和响应的头信息,请求和响应的正文及元数据
        return  Logger.Level.BASIC;//自己可以查看其它日志级别   仅记录请求方法、URL、响应状态码及执行时间
    }
}
