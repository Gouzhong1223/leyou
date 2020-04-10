package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Gouzhong
 * @Description : 这是Service服务的启动类
 * @date Create By Qingsong in 0:03 2019/8/15
 * @email : 1162864960@qq.com
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.leyou.item.mapper")
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class);
    }
}
