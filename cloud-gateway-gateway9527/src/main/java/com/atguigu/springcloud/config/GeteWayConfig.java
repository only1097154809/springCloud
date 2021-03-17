package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeteWayConfig {

    /**
     * 自定义 路由网关
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        //意思就是访问该项目 /bibi 的路径 将会转发跳转到  https://www.bilibili.com 这个页面
        routes.route("path_route_atguigu", r -> r.path("/bibi").uri("https://www.bilibili.com")).build();
        return routes.build();

    }

}
