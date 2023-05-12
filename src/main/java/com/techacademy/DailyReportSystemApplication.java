package com.techacademy;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyReportSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyReportSystemApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("JST"));
    }
}
