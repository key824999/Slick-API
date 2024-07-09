package toy.slick.service;

import org.springframework.stereotype.Service;
import toy.slick.repository.redis.CacheRepository;

@Service
public class CacheService {
    private final CacheRepository cacheRepository;

    public CacheService(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public void delete() {
        cacheRepository.delete();
    }

    public void delete(String cacheName) {
        cacheRepository.delete(cacheName);
    }

    public void delete(String cacheName, String objectName) {
        cacheRepository.delete(cacheName, objectName);
    }

    public void delete(String cacheName, String objectName, String methodName) {
        cacheRepository.delete(cacheName, objectName, methodName);
    }
}
