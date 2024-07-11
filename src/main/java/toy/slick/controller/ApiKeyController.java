package toy.slick.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.slick.common.Response;
import toy.slick.controller.vo.request.ApiKeyReq;
import toy.slick.controller.vo.response.ApiKeyRes;
import toy.slick.interceptor.ApiKeyInterceptor;
import toy.slick.repository.mongo.ApiKeyRepository;
import toy.slick.service.ApiKeyService;

@Slf4j
@RestController
@RequestMapping("/api/apiKey")
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @ApiKeyInterceptor.IsAdmin
    @PostMapping("")
    public Response<ApiKeyRes> postApiKey(@Valid ApiKeyReq apiKeyReq) {
        ApiKeyRepository.ApiKey newApiKey = apiKeyService.insertApiKey(apiKeyReq);

        return new Response<>(ApiKeyRes.builder()
                .apiKey(newApiKey.getApiKey())
                .email(newApiKey.getEmail())
                .expiredZonedDateTime(newApiKey.getExpiredZonedDateTime())
                .build());
    }
}
