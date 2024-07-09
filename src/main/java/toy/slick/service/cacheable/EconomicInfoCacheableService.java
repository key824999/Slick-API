package toy.slick.service.cacheable;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import toy.slick.config.RedisConfig;
import toy.slick.repository.mongo.EconomicEventRepository;
import toy.slick.repository.mongo.FearAndGreedRepository;
import toy.slick.service.EconomicInfoService;

import java.util.Date;
import java.util.List;

@Service
public class EconomicInfoCacheableService extends EconomicInfoService {
    public EconomicInfoCacheableService(FearAndGreedRepository fearAndGreedRepository, EconomicEventRepository economicEventRepository) {
        super(fearAndGreedRepository, economicEventRepository);
    }

    @Override
    @Cacheable(cacheNames = {RedisConfig.CacheNames._10min})
    public FearAndGreedRepository.FearAndGreed findRecentFearAndGreed() {
        return super.findRecentFearAndGreed();
    }

    @Override
    @Cacheable(cacheNames = {RedisConfig.CacheNames._10min})
    public List<EconomicEventRepository.EconomicEvent> findEconomicEventList(Date date) {
        return super.findEconomicEventList(date);
    }
}
