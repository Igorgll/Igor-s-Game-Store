package com.example.demo.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

    @Bean
	public OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Projeto - Game Store")
					.description("Igor's Game Store Api")
					.version("v0.0.1")
				.license(new License()
					.name("Igor's Game Store")
					.url("https://github.com/igorgll/igor's-Game-Store"))
				.contact(new Contact()
					.name("Igor Lima")
					.url("https://github.com/igorgll/igor's-Game-Store")
					.email("igorlimagn@gmail.com")))
				.externalDocs(new ExternalDocumentation()
					.description("Github")
					.url("https://github.com/igorgll/igor's-Game-Store"));
	}

    @Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Success."));
				apiResponses.addApiResponse("201", createApiResponse("Created."));
				apiResponses.addApiResponse("400", createApiResponse("Request Error."));
				apiResponses.addApiResponse("401", createApiResponse("Unauthorized."));
				apiResponses.addApiResponse("404", createApiResponse("Not Found."));
				apiResponses.addApiResponse("500", createApiResponse("Internal Server Error."));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}

}