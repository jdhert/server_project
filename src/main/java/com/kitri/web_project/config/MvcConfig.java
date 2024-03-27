package com.kitri.web_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String basePath = System.getProperty("user.dir");
        String externalResourcePath = Paths.get(basePath).getParent().resolve("images").toUri().toString();

        registry.addResourceHandler("/images/**").addResourceLocations(externalResourcePath);
    }
}