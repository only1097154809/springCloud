package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Long id) {
        return "ok 这个方法 调用服务降级成功!";
    }

    @Override
    public String paymentInfo_TimeOut(Long id) {
        return "超时这个 这个方法 调!用!服!务!降!级!成功!";
    }
}
