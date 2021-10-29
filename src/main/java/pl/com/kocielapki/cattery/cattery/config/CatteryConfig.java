package pl.com.kocielapki.cattery.cattery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
public class CatteryConfig {
    @Value("${cattery-path-to-gallery}")
    private String pathToGallery;

    public String getPathToGallery() {
        return pathToGallery;
    }
}
