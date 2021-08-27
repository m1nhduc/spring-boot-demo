package dmd.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/resources/bootstrap/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/5.1.0/");

        registry
            .addResourceHandler("/resources/jquery/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.6.0/");
    }
}
