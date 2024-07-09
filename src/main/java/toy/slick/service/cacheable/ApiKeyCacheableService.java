package toy.slick.service.cacheable;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import toy.slick.config.RedisConfig;
import toy.slick.repository.mongo.ApiKeyRepository;
import toy.slick.service.ApiKeyService;

import java.util.Optional;

@Service
public class ApiKeyCacheableService extends ApiKeyService {

    public ApiKeyCacheableService(ApiKeyRepository apiKeyRepository) {
        super(apiKeyRepository);
    }

    @Override
    @Cacheable(cacheNames = {RedisConfig.CacheNames._1hour})
    public Optional<ApiKeyRepository.ApiKey> findByApiKey(String requestApiKey) {
        return super.findByApiKey(requestApiKey);
    }
}
