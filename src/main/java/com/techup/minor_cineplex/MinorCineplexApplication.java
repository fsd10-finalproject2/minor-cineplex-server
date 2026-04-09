package com.techup.minor_cineplex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class MinorCineplexApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinorCineplexApplication.class, args);
	}

}
