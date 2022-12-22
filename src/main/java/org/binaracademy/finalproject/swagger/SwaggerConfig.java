package org.binaracademy.finalproject.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api(){
        String[] paths = {"/**"};
        String[] packagesToScan = {"org.binaracademy.finalproject.controllers"};
        return GroupedOpenApi.builder()
                .group("base-api")
                .pathsToMatch(paths)
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("E-Flight API") String appTitle,
                                 @Value("v1.0.0") String appVersion){
        return new OpenAPI()
                .info(new Info().title(appTitle)
                        .description("Ini adalah documentasi API Final Project untuk web E-flight\n\n" +
                                "API documentasi yang dapat dilihat oleh frontEnd untuk dapat memahami dan menggunakan API yang tersedia,\n" +
                                "didalam API ini tedapat beberapa controller yang dapat digunakan oleh FrontEnd Untuk keperluan Final Project")
                        .version(appVersion)
                        .license(new License()
                                .name("Apcahe 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact()
                                .name("BE Kelompok6")
                                .email("bekel6@gmail.com")));
    }
}
