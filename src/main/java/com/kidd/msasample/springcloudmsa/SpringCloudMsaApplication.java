package com.kidd.msasample.springcloudmsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudMsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudMsaApplication.class, args);
    }

}
