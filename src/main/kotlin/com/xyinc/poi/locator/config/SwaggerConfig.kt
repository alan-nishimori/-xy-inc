package com.xyinc.poi.locator.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket =
        Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.xyinc.poi.locator"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .apiInfo(
                ApiInfoBuilder()
                    .title("XY Inc Point of Interest Locator")
                    .description("API to register and consult points of interest")
                    .contact(
                        Contact(
                            "Alan Nishimori Araujo",
                            "https://github.com/alan-nishimori",
                            "alan.nishimori@gmail.com"
                        )
                    )
                    .build()
            )
}
