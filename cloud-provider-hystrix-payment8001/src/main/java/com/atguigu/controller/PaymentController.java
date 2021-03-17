package com.atguigu.controller;

import com.atguigu.service.PaymentServiceInf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentServiceInf paymentServiceInf;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 正常访问
     */
    @GetMapping(value = "/payment/hystrix/paymentInfo_OK/{id}")
    public String paymentInfo_OK(@PathVariable("id")Long id){
        return paymentServiceInf.paymentInfo_OK(id);
    }

    /**
     * 超时访问
     */
    @GetMapping(value ="/payment/hystrix/paymentInfo_TimeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Long id) {
        String result = paymentServiceInf.paymentInfo_TimeOut(id);
        log.info("*****result:" + result);
        return result;

    }

    /*+
        服务熔断 
     */
    @GetMapping(value ="/payment/hystrix/paymentCircuitBreaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentServiceInf.paymentCircuitBreaker(id);
        log.info("*****result:" + result);
        return result;

    }

}
