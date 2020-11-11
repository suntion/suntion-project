package com.suntion.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @program: suntion-project
 * @Description: 123
 * @Author: Shen.Sun
 * @create: 2020-04-02 17:33
 **/
@SpringBootApplication
@EnableConfigurationProperties
public class KafkaConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }
}
