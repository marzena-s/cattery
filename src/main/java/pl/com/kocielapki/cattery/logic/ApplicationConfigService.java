package pl.com.kocielapki.cattery.logic;

import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ApplicationConfigService {
    private BuildProperties buildProperties;

    public ApplicationConfigService(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Cacheable(CacheType.APP_VERSION)
    public String getVersion() {
        return buildProperties.getVersion();
    }

}
