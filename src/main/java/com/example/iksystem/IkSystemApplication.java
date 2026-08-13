package com.example.iksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * Bu sınıf Ik System Application nesnesini temsil eder.
 */

@SpringBootApplication
@EnableScheduling
public class IkSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(IkSystemApplication.class, args);
    }

}
