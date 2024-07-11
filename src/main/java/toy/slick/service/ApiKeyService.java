package toy.slick.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import toy.slick.common.Const;
import toy.slick.controller.vo.request.ApiKeyReq;
import toy.slick.repository.mongo.ApiKeyRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKeyRepository.ApiKey insertApiKey(ApiKeyReq apiKeyReq) {
        ApiKeyRepository.ApiKey newApiKey = ApiKeyRepository.ApiKey.builder()
                .apiKey(DigestUtils.sha512Hex(apiKeyReq.hashCode() + apiKeyReq.getEmail() + System.currentTimeMillis()))
                .email(apiKeyReq.getEmail())
                .role("user")
                .useYn(true)
                .requestCntPer30Sec(30)
                .expiredZonedDateTime(ZonedDateTime.now(ZoneId.of(Const.ZoneId.UTC)).plusYears(1).toString())
                .build();

        String _id = newApiKey.getEmail();

        return apiKeyRepository.insert(newApiKey.toMongoData(_id));
    }

    public Optional<ApiKeyRepository.ApiKey> findByApiKey(String requestApiKey) {
        return apiKeyRepository.findByApiKey(requestApiKey);
    }
}
