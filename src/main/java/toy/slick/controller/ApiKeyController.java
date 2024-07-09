package toy.slick.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.slick.common.Response;
import toy.slick.controller.vo.ApiKey;
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
    public Response<ApiKeyRepository.ApiKey> postApiKey(@Valid ApiKey apiKey) {
        return new Response<>(apiKeyService.insertApiKey(apiKey));
    }
}
