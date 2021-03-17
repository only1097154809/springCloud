package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService ;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getid(@PathVariable("id")Long id){
        log.info("*******************feign调用服务: "+id);
         return    paymentFeignService.get(id);
    }

    @GetMapping(value = "/consumer/payment/openFeign")
    public String openFeign() {
        return paymentFeignService.openFeign();
    }
}
