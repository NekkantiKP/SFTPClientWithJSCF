package net.guides.springboot2.springboot2webappjsp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping("/bootcrud/products")
        .allowedOrigins("http://localhost:8090")
		.allowedMethods("GET")
		.allowedHeaders("*")
		.maxAge(3600)
		 .allowCredentials(false);
    }
}
