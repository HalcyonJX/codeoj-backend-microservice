package com.halcyon.codeojbackendjudgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.halcyon")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.halcyon.codeojbackendserviceclient.service"})
public class CodeojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeojBackendJudgeServiceApplication.class, args);
    }

}
