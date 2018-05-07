package com.mydao.nsop.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NsopClientMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(NsopClientMainApplication.class, args);
	}
}
