package dev.topcolleguesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TopColleguesApiApplication {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*")
						.allowedMethods("GET", "POST", "PATCH", "PUT", "OPTIONS", "DELETE").allowCredentials(true);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(TopColleguesApiApplication.class, args);
	}

}
