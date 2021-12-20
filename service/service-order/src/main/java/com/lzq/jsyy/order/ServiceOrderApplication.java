package com.lzq.jsyy.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lzq
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lzq")
@EnableDiscoveryClient
@EnableCaching
@EnableFeignClients(basePackages = "com.lzq")
@EnableTransactionManagement
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
