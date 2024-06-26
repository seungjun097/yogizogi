package com.green.yogizogi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YogizogiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YogizogiApplication.class, args);
	}
}
