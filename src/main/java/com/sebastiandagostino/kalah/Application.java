package com.sebastiandagostino.kalah;

import com.sebastiandagostino.kalah.interceptor.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@PropertySource("classpath:kalah.properties")
@SpringBootApplication
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/v1/**");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(generateApiInfo());
    }

    private ApiInfo generateApiInfo() {
        return new ApiInfoBuilder()
                .title("Kalah")
                .description("Kalah is one of the oldest known games to still be widely played today. " +
                        "Kalah is a generic name for a family of two-player turn-based strategy board games " +
                        "played with small stones, beans, or seeds and rows of holes or pits in the earth, a " +
                        "board or other playing surface. The objective is usually to capture all or some set of " +
                        "the opponent's pieces.")
                .version("1.0-SNAPSHOT")
                .contact(new Contact("Sebastian D'Agostino", "", "sebastiandagostino@gmail.com"))
                .build();
    }

}
