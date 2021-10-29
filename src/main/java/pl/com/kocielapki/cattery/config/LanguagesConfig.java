package pl.com.kocielapki.cattery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;


@Configuration
public class LanguagesConfig implements WebMvcConfigurer {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        ApplicationLocaleResolver applicationLocaleResolver = new ApplicationLocaleResolver();
        applicationLocaleResolver.setDefaultLocale(Locale.forLanguageTag("pl-PL"));
        return applicationLocaleResolver;
    }

}
