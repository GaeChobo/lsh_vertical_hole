package kr.movements.vertical.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * @file name : SwaggerConfiguration.java
 * @project : spring-boot-init
 * @date : Feb 20, 2023
 * @author : "ckr"
 * @history:
 * @program comment :
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("kr.movements.vertical"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData())
				.securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Collections.singletonList(apiKey()));
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Vertical-Hole REST API")
				.description("dev")
				.version("0.0.1")
				.termsOfServiceUrl("Terms of service")
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.build();
	}

	@Bean
	public UiConfiguration uiConfiguration(){
		return UiConfigurationBuilder
				.builder()
				.operationsSorter(OperationsSorter.ALPHA)
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.any())
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
	}
}
