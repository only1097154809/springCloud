package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义负载均衡规则
 */
@Configuration
public class MySelfRule {

    /**
     * 随机的 负载均衡
     */
    @Bean
    public IRule myRule(){
       // return  new RandomRule();
        return new RoundRobinRule();
    }
}
