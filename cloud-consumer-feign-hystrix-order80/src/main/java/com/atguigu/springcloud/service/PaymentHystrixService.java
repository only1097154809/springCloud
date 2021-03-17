package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {
    //正常访问
    @GetMapping(value = "/payment/hystrix/paymentInfo_OK/{id}")
    public String paymentInfo_OK(@PathVariable("id")Long id);

    //超时访问
    @GetMapping(value ="/payment/hystrix/paymentInfo_TimeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Long id);

}
