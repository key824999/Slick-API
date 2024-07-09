package toy.slick.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import toy.slick.common.Const;
import toy.slick.exception.RequestApiKeyException;
import toy.slick.repository.mongo.ApiKeyRepository;
import toy.slick.service.cacheable.ApiKeyCacheableService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {
    private final ApiKeyCacheableService apiKeyCacheableService;

    public ApiKeyInterceptor(ApiKeyCacheableService apiKeyCacheableService) {
        this.apiKeyCacheableService = apiKeyCacheableService;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IsAdmin {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestApiKey = request.getHeader("requestApiKey");

        if (StringUtils.isBlank(requestApiKey)) {
            throw new RequestApiKeyException("Value of requestApiKey is blank");
        }

        Optional<ApiKeyRepository.ApiKey> apiKey = apiKeyCacheableService.findByApiKey(requestApiKey);

        if (apiKey.isEmpty()) {
            throw new RequestApiKeyException("ApiKey is empty");
        }

        if (!apiKey.get().isUseYn()) {
            throw new RequestApiKeyException("ApiKey is unusable");
        }

        if (ZonedDateTime.now(ZoneId.of(Const.ZoneId.UTC))
                .isAfter(ZonedDateTime.parse(apiKey.get().getExpiredZonedDateTime()))) {
            throw new RequestApiKeyException("ApiKey is expired");
        }

        // if 404 request, handler -> HandlerMethod casting exception
        if (handler instanceof HandlerMethod handlerMethod) {
            if (handlerMethod.getMethodAnnotation(IsAdmin.class) != null) {
                if (!"admin".equals(apiKey.get().getRole())) {
                    throw new RequestApiKeyException("ApiKey has not appropriate role");
                }
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
