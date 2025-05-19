package com.halcyon.codeojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.halcyon.codeojbackenduserservice.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan("com.halcyon")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.halcyon.codeojbackendserviceclient.service"})
public class CodeojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeojBackendUserServiceApplication.class, args);
    }

}
