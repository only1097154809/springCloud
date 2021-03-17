package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    //public static  final  String PAYMENT_URL ="http://localhost:8001";
    public static  final  String PAYMENT_URL ="http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate ;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private LoadBalancer loadBalancer;

    /**
     * 使用 postForObject 新增
     */
    @GetMapping(value = "/consumer/payment/add")
    public CommonResult<Payment> create (Payment payment){
        log.info("******************新增");
        return restTemplate.postForObject(PAYMENT_URL+"/payment/add",payment,CommonResult.class);
    }

    /**
     * 使用 postEntity 新增
     */
    @GetMapping(value = "/consumer/payment/postEntity")
    public CommonResult<Payment> create2 (Payment payment){
        ResponseEntity<CommonResult> commonResultResponseEntity = restTemplate.postForEntity(PAYMENT_URL + "/payment/add", payment, CommonResult.class);
        if(commonResultResponseEntity.getStatusCode().is2xxSuccessful()){
            log.info("******************: "+commonResultResponseEntity.getStatusCode()+"\t"+commonResultResponseEntity.getStatusCodeValue()+ "\t" +commonResultResponseEntity.getHeaders());
            return commonResultResponseEntity.getBody();
        }else{
            return new CommonResult(444,"新增失败");
        }
    }

    /**
     * 使用 getForObject 查询
     */
    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")Long id){
        log.info("******************查询: "+id);
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }


    /**
     * 使用 getForEntity 查询
     */
    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id")Long id){
        ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if(forEntity.getStatusCode().is2xxSuccessful()){
            log.info("******************: "+forEntity.getStatusCode()+"\t"+forEntity.getStatusCodeValue()+ "\t" +forEntity.getHeaders());
            return forEntity.getBody();
        }else{
            return new CommonResult(444,"操作失败");
        }
    }


    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(null == instances || instances.size() == 0){
            return null;
        }
        ServiceInstance instance = loadBalancer.serviceInstance(instances);
        URI uri = instance.getUri();//获取调用的路径

        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }
}
