package com.halcyon.codeojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.halcyon.codeojbackendquestionservice.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan("com.halcyon")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.halcyon.codeojbackendserviceclient.service"})
public class CodeojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeojBackendQuestionServiceApplication.class, args);
    }

}
