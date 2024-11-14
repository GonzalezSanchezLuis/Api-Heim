package com.holi.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.holi.api")
public class HoliApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoliApiApplication.class, args);
	}

}
