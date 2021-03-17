package com.atguigu.springcloud.lb.impl;

import com.atguigu.springcloud.lb.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer {

    private AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);

    public final int  getInstance  (){
        int current;
        int next ;
        do {
            current = this.nextServerCyclicCounter.get();
            next = current >= 2147483647 ? 0: current + 1 ;
        }while (!this.nextServerCyclicCounter.compareAndSet(current, next));
        System.out.println("*********第几次调用服务:"+next);
        return next;
    }

    /**\
     * 获取这次要 调用的 微服务
     */
    @Override
    public ServiceInstance serviceInstance(List<ServiceInstance> serviceInstanceList) {
        int index = getInstance() % serviceInstanceList.size();//获取该微服务的节点数(集群数量)
        return serviceInstanceList.get(index);
    }

}
