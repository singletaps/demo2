package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {
        "com.example.demo",
        "com.example.demo.login",
        "com.example.demo.Customer",
        "com.example.demo.Order",
        "com.example.demo.Invoice",
        "com.example.demo.Product",
        "com.example.demo.Schedule",
        "com.example.demo.Substation"
})
@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }
}
