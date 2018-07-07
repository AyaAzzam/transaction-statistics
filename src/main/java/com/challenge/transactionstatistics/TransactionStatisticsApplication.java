package com.challenge.transactionstatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionStatisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionStatisticsApplication.class, args);
	}
}
