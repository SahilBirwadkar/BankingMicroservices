package com.sahil.programming.cards;

import com.sahil.programming.cards.dtos.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "Cards microservice REST API Documentation",
				version = "1.0.0",
				contact = @Contact(
						name = "Sahil Birwadkar",
						email = "sahil@gmail.com",
						url = "https://www.sahilbirwadkar.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://localhost:9000/swagger-ui/index.html"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Cards microservice REST API Documentation",
				url = "http://localhost:9000/swagger-ui/index.html"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
