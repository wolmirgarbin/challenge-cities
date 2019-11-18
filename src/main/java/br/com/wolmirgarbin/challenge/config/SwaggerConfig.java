package br.com.wolmirgarbin.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.wolmirgarbin.challenge.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Desafio API para cidades",
                "Esta API foi construida para resolver o problema do desafio de java da Evoluum.",
                "1.0",
                "Terms of service",
                new Contact("Wolmir Garbin", "www.linkedin.com/in/wolmir-cezer-garbin-b553b938/", "wolmirgarbin@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }
}
