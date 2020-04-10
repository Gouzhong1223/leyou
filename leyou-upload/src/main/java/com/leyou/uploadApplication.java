package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author : Gouzhong
 * @company : www.gouzhong1223.com
 * @Description :
 * @date : create by QingSong in 2019-08-21 0:11
 * @email : 1162864960@qq.com
 */
@SpringBootApplication
@EnableEurekaClient
public class uploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(uploadApplication.class);
    }
}
