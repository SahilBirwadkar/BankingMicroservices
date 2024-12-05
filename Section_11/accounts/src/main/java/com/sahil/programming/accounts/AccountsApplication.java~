package com.sahil.programming.accounts;

import com.sahil.programming.accounts.dtos.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


// go to "http://localhost:8080/swagger-ui/index.html" website to see swagger documentation
// make sure we're adding "springdoc-openapi-starter-webmvc-ui" dependency in pom.xml file to create a documentation of code.

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value={AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Account microservice REST API documentation",
				description = "Account microservice REST API documentation",
				version = "1.0.0",
				contact = @Contact(
						name = "Sahil Birwadkar",
						email = "sahil@gmail.com",
						url = "https://www.sahilbirwadkar.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://localhost:8080/swagger-ui/index.html"
				)
		)
)
public class AccountsApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
