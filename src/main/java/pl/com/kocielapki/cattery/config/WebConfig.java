package pl.com.kocielapki.cattery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private ApplicationConfig applicationConfig;

    public WebConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/img/**",
                "/cattery_css/**",
                "/libs/**",
                "/fonts/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/static/img/",
                        "classpath:/static/cattery_css/",
                        "classpath:/static/libs/",
                        "classpath:/static/fonts/",
                        "classpath:/static/js/");

        registry.addResourceHandler("/gallery/**")
                .addResourceLocations(new File(applicationConfig.getPathToImagesFolder()).toURI().toString());
    }

}
