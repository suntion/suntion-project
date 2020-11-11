package com.suntion.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @program: suntion-project
 * @Description: 123
 * @Author: Shen.Sun
 * @create: 2020-04-02 17:25
 **/
@SpringBootApplication
@EnableConfigurationProperties
public class KafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }
}
