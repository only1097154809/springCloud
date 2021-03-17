package com.atguigu.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.service.PaymentServiceInf;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService  implements PaymentServiceInf {
    /*
        正常访问
     */
    public String paymentInfo_OK(Long id){
        return "线程池 " + Thread.currentThread().getName() +"\t"+ "paymentInfo_OK "+id ;
    }

    /*
        超时访问
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")}//超过3秒的时间则调用降级
    )
    public String paymentInfo_TimeOut(Long id) {
        int timeNumber = 4000;
        try {
            // 暂停3秒钟
            TimeUnit.MILLISECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id:" + id + "\t" +
                "O(∩_∩)O哈哈~  耗时(秒)" ;
    }
    public String paymentInfo_TimeOutHandler(Long id){
        return "线程池:" + Thread.currentThread().getName() + " paymentInfo_TimeOut,id:" + id + "\t" +
                "O(∩_∩)O哈哈~  耗时(秒)" + " 服务降级";
    }

    //**====== 服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id){
        if(id < 0){
          throw   new RuntimeException("**********id 不能为负数");
        }
        String s = IdUtil.objectId();
        return "调用吃成功: "+ s + " , 业务流水号为: " + IdUtil.simpleUUID().toString();
    }
    private String paymentCircuitBreaker_fallback(Integer id){
        return "服务熔断的服务降级方法ing....";
    }


}
