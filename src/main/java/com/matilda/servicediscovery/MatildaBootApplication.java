package com.matilda.servicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@EnableAutoConfiguration  
public class MatildaBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MatildaBootApplication.class, args);
    }
    
   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MatildaBootApplication.class);
    }*/
}
