package toy.slick.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import toy.slick.controller.vo.ApiKey;
import toy.slick.repository.mongo.ApiKeyRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKeyRepository.ApiKey insertApiKey(ApiKey apiKey) {
        ApiKeyRepository.ApiKey newApiKey = ApiKeyRepository.ApiKey.builder()
                .apiKey(DigestUtils.sha512Hex(apiKey.hashCode() + apiKey.getEmail() + System.currentTimeMillis()))
                .email(apiKey.getEmail())
                .role("user")
                .useYn(true)
                .requestCntPer30Sec(30)
                .expiredZonedDateTime(ZonedDateTime.now().plusYears(1).toString())
                .build();

        String _id = newApiKey.getEmail();

        return apiKeyRepository.insert(newApiKey.toMongoData(_id));
    }

    public Optional<ApiKeyRepository.ApiKey> findByApiKey(String requestApiKey) {
        return apiKeyRepository.findByApiKey(requestApiKey);
    }
}
