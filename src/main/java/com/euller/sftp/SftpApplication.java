package com.euller.sftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class SftpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SftpApplication.class, args);
    }

}
