package toy.slick.service.cacheable;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import toy.slick.config.RedisConfig;
import toy.slick.repository.mongo.DJIRepository;
import toy.slick.repository.mongo.EconomicEventRepository;
import toy.slick.repository.mongo.FearAndGreedRepository;
import toy.slick.service.EconomicInfoService;

import java.util.Date;
import java.util.List;

@Service
public class EconomicInfoCacheableService extends EconomicInfoService {
    public EconomicInfoCacheableService(FearAndGreedRepository fearAndGreedRepository,
                                        EconomicEventRepository economicEventRepository,
                                        DJIRepository djiRepository) {
        super(fearAndGreedRepository, economicEventRepository, djiRepository);
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

    @Override
    @Cacheable(cacheNames = {RedisConfig.CacheNames._1min})
    public DJIRepository.DowJonesIndustrialAverage getDJI() {
        return super.getDJI();
    }
}
