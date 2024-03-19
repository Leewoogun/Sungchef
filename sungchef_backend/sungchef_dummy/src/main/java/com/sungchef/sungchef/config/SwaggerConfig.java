package com.sungchef.sungchef.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
//        String jwt = "JWT";
//        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
//                .addSecurityItem(securityRequirement)
//                .components(components);

    }

    private Info apiInfo() {
        return new Info()
                .title("성식당")
                .description("베른에 돌 던지지 말아주세요")
                .version("1.0.0");
    }

}
