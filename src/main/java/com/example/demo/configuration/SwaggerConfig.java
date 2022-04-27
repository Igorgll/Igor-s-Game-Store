package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    @Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Igor's Game Store")
						.description("Game Store API")
						.version("v0.0.1")
						.license(new License()
								.name("Igor's Game Store")
								.url("https://github.com/Igorgll/Igor-s-Game-Store"))
						.contact(new Contact()
								.name("GitHub Game Store")
								.url("https://github.com/Igorgll/Igor-s-Game-Store")
								.email("igorlimagn@gmail.com")))
				.externalDocs(new ExternalDocumentation()
						.description("Projeto Github")
						.url("https://github.com/Igorgll/Igor-s-Game-Store"));
	}
}
