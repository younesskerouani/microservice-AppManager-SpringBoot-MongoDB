package com.ixpath.appmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories
@SpringBootApplication

public class AppsEnvApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppsEnvApplication.class, args);
    }

}
