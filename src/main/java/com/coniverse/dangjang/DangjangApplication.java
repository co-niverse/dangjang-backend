package com.coniverse.dangjang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DangjangApplication {
	public static void main(String[] args) {
		SpringApplication.run(DangjangApplication.class, args);
	}
}
