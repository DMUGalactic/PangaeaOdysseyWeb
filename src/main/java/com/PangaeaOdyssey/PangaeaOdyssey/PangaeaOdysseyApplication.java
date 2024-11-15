package com.PangaeaOdyssey.PangaeaOdyssey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PangaeaOdysseyApplication {
	public static void main(String[] args) {SpringApplication.run(PangaeaOdysseyApplication.class, args);

	}
}