package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource(name="paymentService")
    private PaymentService paymentService ;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


    @PostMapping(value ="/payment/add")
    public CommonResult add(@RequestBody Payment payment){
        log.info("********"+"新增数据 为: " + payment.getSerial() + " ************");
        int result =paymentService.add(payment);
        if(result < 1){
            return new CommonResult(400 ,"新增失败",null);
        }else{
            return new CommonResult(200 ,"新增成功,serverPort:"+serverPort,result);
        }
    }

    @GetMapping(value ="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable ("id")Long id){
        log.info("********"+"查询ID 为: " + id + " ************");
       Payment result =paymentService.get(id);
        if(result == null){
            return new CommonResult(200 ,"没有数据",null);
        }else{
            return new CommonResult(200 ,"查询成功,serverPort:"+serverPort,result);
        }
    }

    /**
     * 获取 项目中的 微服务信息
     * @return
     */
    @GetMapping(value ="/payment/discovery")
    public Object discovery(){
        List<String> list  = discoveryClient.getServices();
        for (String services: list) {
            log.info("*****services: "+ services);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance :instances) {
            log.info("*****instances: "+ instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping(value = "/payment/openFeign")
    public String openFeign() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "feign超时连接的测试 "+serverPort;
    }

}


