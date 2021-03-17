package com.atguigu.service;

public interface PaymentServiceInf {
    public String paymentInfo_OK(Long id);

    public String paymentInfo_TimeOut(Long id);

    public String paymentCircuitBreaker(Integer id);
}
