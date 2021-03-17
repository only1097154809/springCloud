package com.atguigu.springcloud.controller;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderConsulController {

    public static  final  String PAYMENT_URL ="http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate ;

    @RequestMapping(value = "/consumer/payment/consul")
    public String paymentzk()
    {
        return restTemplate.getForObject(PAYMENT_URL+"/payment/consul",String.class);
    }
}
