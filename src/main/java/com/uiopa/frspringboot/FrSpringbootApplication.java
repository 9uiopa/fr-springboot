package com.uiopa.frspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class FrSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FrSpringbootApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}
}
