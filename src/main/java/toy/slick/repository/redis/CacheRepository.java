package toy.slick.repository.redis;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;
import toy.slick.config.RedisConfig;

@Repository
public class CacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void delete() {
        try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions()
                .match(RedisConfig.CacheNames.prefix + "*")
                .build())) {
            while (cursor.hasNext()) {
                redisTemplate.delete(cursor.next());
            }
        }
    }

    public void delete(String cacheName) {
        try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions()
                .match(RedisConfig.CacheNames.prefix + cacheName + "::*")
                .build())) {
            while (cursor.hasNext()) {
                redisTemplate.delete(cursor.next());
            }
        }
    }

    public void delete(String cacheName, String objectName) {
        try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions()
                .match(RedisConfig.CacheNames.prefix + cacheName + "::" + objectName + "\\.*")
                .build())) {
            while (cursor.hasNext()) {
                redisTemplate.delete(cursor.next());
            }
        }
    }

    public void delete(String cacheName, String objectName, String methodName) {
        try (Cursor<String> cursor = redisTemplate.scan(ScanOptions.scanOptions()
                .match(RedisConfig.CacheNames.prefix + cacheName + "::" + objectName + "\\." + methodName + "\\(*")
                .build())) {
            while (cursor.hasNext()) {
                redisTemplate.delete(cursor.next());
            }
        }
    }
}
