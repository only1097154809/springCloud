package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
/*@DefaultProperties(defaultFallback = "paymentInfo_TimeOutFallMethod",commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")})//服务降级*/
public class OrderHyrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consuer/payment/hystrix/paymentInfo_OK/{id}")
    public String paymentInfo_OK (@PathVariable("id")Long id){
        return paymentHystrixService.paymentInfo_OK(id);
    }


    @GetMapping(value = "/consuer/payment/hystrix/paymentInfo_TimeOut/{id}")
   /* @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutFall" ,commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")}//超过5秒的时间则调用降级)
    )*/
   //@HystrixCommand
    public String paymentInfo_TimeOut (@PathVariable("id")Long id){
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }
    /*
     服务降级
     */
    public String paymentInfo_TimeOutFall(@PathVariable("id")Long id){
        return "id :" +id+" 调用客户端通用的defaultFallback的服务降级方法";
    }

    public String paymentInfo_TimeOutFallMethod(){
        return "系统繁忙~ 请稍后再试";
    }
}
