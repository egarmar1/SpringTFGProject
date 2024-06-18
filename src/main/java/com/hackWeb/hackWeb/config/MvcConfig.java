package com.hackWeb.hackWeb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final List<String> UPLOAD_DIRS = List.of("photos", "videos");

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        UPLOAD_DIRS.forEach( uploadDir -> exposeDirectory(uploadDir,registry));
    }

    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir);

        registry.addResourceHandler("/" + uploadDir + "/*")
                .addResourceLocations("file:" + uploadPath.toAbsolutePath() + "/");

    }
}
