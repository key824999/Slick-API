package toy.slick.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import toy.slick.common.Const;
import toy.slick.controller.vo.request.EconomicEvent;
import toy.slick.controller.vo.request.FearAndGreed;
import toy.slick.repository.mongo.EconomicEventRepository;
import toy.slick.repository.mongo.FearAndGreedRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EconomicInfoService {
    private final FearAndGreedRepository fearAndGreedRepository;
    private final EconomicEventRepository economicEventRepository;

    public EconomicInfoService(FearAndGreedRepository fearAndGreedRepository, EconomicEventRepository economicEventRepository) {
        this.fearAndGreedRepository = fearAndGreedRepository;
        this.economicEventRepository = economicEventRepository;
    }

    public FearAndGreedRepository.FearAndGreed findRecentFearAndGreed() {
        return fearAndGreedRepository
                .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "_id")))
                .getContent()
                .getFirst();
    }

    public void saveFearAndGreed(FearAndGreed fearAndGreed) {
        String dataId = ZonedDateTime.now(ZoneId.of(Const.ZoneId.UTC)).format(Const.DateTimeFormat.yyyyMMddHH.getDateTimeFormatter());

        fearAndGreedRepository.save(FearAndGreedRepository.FearAndGreed.builder()
                .rating(fearAndGreed.getRating())
                .score(fearAndGreed.getScore())
                .build()
                .toMongoData(dataId));
    }

    public List<EconomicEventRepository.EconomicEvent> findEconomicEventList(@PathVariable("yyyy-MM-dd_UTC") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of(Const.ZoneId.UTC));

        Date gteDate = Date.from(zonedDateTime
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0).toInstant());

        Date ltDate = Date.from(zonedDateTime
                .plusDays(1)
                .withHour(4)
                .withMinute(40)
                .withSecond(0)
                .withNano(0).toInstant());

        return economicEventRepository.findAll(gteDate, ltDate);
    }

    public void saveEconomicEvent(EconomicEvent economicEvent) {
        String dataId = economicEvent.getId();

        economicEventRepository.save(EconomicEventRepository.EconomicEvent.builder()
                .dateUTC(Date.from(economicEvent.getZonedDateTime().toInstant()))
                .id(economicEvent.getId())
                .name(economicEvent.getName())
                .country(economicEvent.getCountry())
                .importance(economicEvent.getImportance())
                .actual(economicEvent.getActual())
                .forecast(economicEvent.getForecast())
                .previous(economicEvent.getPrevious())
                .build()
                .toMongoData(dataId));
    }

    public void saveEconomicEventList(List<EconomicEvent> economicEventList) {
        economicEventRepository.saveAll(economicEventList
                .stream()
                .map(economicEvent -> {
                    String dataId = economicEvent.getId();

                    return EconomicEventRepository.EconomicEvent.builder()
                            .dateUTC(Date.from(economicEvent.getZonedDateTime().toInstant()))
                            .id(economicEvent.getId())
                            .name(economicEvent.getName())
                            .country(economicEvent.getCountry())
                            .importance(economicEvent.getImportance())
                            .actual(economicEvent.getActual())
                            .forecast(economicEvent.getForecast())
                            .previous(economicEvent.getPrevious())
                            .build()
                            .toMongoData(dataId);
                })
                .collect(Collectors.toList()));
    }
}
