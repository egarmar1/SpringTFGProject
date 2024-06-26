package com.hackWeb.hackWeb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final List<String> UPLOAD_DIRS = List.of("photos", "videos");

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        UPLOAD_DIRS.forEach(uploadDir -> exposeDirectory(uploadDir, registry));
    }

    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
        try (Stream<Path> paths = Files.walk(Paths.get(uploadDir))) {
            List<Path> subDirs = paths.filter(Files::isDirectory).toList();
            for (Path subDir : subDirs) {
                String resourcePattern = subDir.toString().replace("\\", "/") + "/**";
                String resourceLocation = "file:" + subDir.toAbsolutePath().toString().replace("\\", "/") + "/";
                registry.addResourceHandler("/" + resourcePattern)
                        .addResourceLocations(resourceLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
