package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Gouzhong
 * @Description : 这是网关的启动类
 * @date Create By Qingsong in 23:01 2019/8/14
 * @email : 1162864960@qq.com
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GetWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GetWayApplication.class);
    }
}
