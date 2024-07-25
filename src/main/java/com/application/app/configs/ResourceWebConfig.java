package com.application.app.configs;

import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ResourceWebConfig implements WebMvcConfigurer {

    final Environment environment;

    public ResourceWebConfig(Environment environment) {
        this.environment = environment;
    }


    public void addSourceHandlers(final ResourceHandlerRegistry registry) {
        String location = environment.getProperty("app.file.storage.mapping");

        registry.addResourceHandler("/uploads/**").addResourceLocations(location);
    }
}
