package com.hotelhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HotelHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelHubApplication.class, args);
    }
}