package com.kolmanfreecss.kolmanfreecss.datagithub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kolman-Freecss
 * @version 1.0
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Kolman-Freecss Server OpenAPI Definition", version = "1.0"))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
