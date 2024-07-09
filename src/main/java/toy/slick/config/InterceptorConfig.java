package toy.slick.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toy.slick.interceptor.ApiKeyInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final ApiKeyInterceptor apiKeyInterceptor;

    public InterceptorConfig(ApiKeyInterceptor apiKeyInterceptor) {
        this.apiKeyInterceptor = apiKeyInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/api/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
