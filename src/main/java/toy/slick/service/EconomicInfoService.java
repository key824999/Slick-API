package toy.slick.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import toy.slick.common.Const;
import toy.slick.controller.vo.request.DJIReq;
import toy.slick.controller.vo.request.EconomicEventReq;
import toy.slick.controller.vo.request.FearAndGreedReq;
import toy.slick.controller.vo.request.IXICReq;
import toy.slick.controller.vo.request.SPXReq;
import toy.slick.repository.mongo.DJIRepository;
import toy.slick.repository.mongo.EconomicEventRepository;
import toy.slick.repository.mongo.FearAndGreedRepository;
import toy.slick.repository.mongo.IXICRepository;
import toy.slick.repository.mongo.SPXRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EconomicInfoService {
    private final FearAndGreedRepository fearAndGreedRepository;
    private final EconomicEventRepository economicEventRepository;
    private final DJIRepository djiRepository;
    private final SPXRepository spxRepository;
    private final IXICRepository ixicRepository;

    public EconomicInfoService(FearAndGreedRepository fearAndGreedRepository,
                               EconomicEventRepository economicEventRepository,
                               DJIRepository djiRepository,
                               SPXRepository spxRepository, IXICRepository ixicRepository) {
        this.fearAndGreedRepository = fearAndGreedRepository;
        this.economicEventRepository = economicEventRepository;
        this.djiRepository = djiRepository;
        this.spxRepository = spxRepository;
        this.ixicRepository = ixicRepository;
    }

    public FearAndGreedRepository.FearAndGreed findRecentFearAndGreed() {
        return fearAndGreedRepository
                .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "_id")))
                .getContent()
                .getFirst();
    }

    public void saveFearAndGreed(FearAndGreedReq fearAndGreedReq) {
        String dataId = ZonedDateTime.now(ZoneId.of(Const.ZoneId.UTC)).format(Const.DateTimeFormat.yyyyMMddHH.getDateTimeFormatter());

        fearAndGreedRepository.save(FearAndGreedRepository.FearAndGreed.builder()
                .rating(fearAndGreedReq.getRating())
                .score(fearAndGreedReq.getScore())
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
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0).toInstant());

        return economicEventRepository.findAll(gteDate, ltDate);
    }

    public void saveEconomicEvent(EconomicEventReq economicEventReq) {
        String dataId = economicEventReq.getId();

        economicEventRepository.save(EconomicEventRepository.EconomicEvent.builder()
                .dateUTC(Date.from(economicEventReq.getZonedDateTime().toInstant()))
                .id(economicEventReq.getId())
                .name(economicEventReq.getName())
                .country(economicEventReq.getCountry())
                .importance(economicEventReq.getImportance())
                .actual(economicEventReq.getActual())
                .forecast(economicEventReq.getForecast())
                .previous(economicEventReq.getPrevious())
                .build()
                .toMongoData(dataId));
    }

    public void saveEconomicEventList(List<EconomicEventReq> economicEventReqList) {
        economicEventRepository.saveAll(economicEventReqList
                .stream()
                .map(economicEventReq -> {
                    String dataId = economicEventReq.getId();

                    return EconomicEventRepository.EconomicEvent.builder()
                            .dateUTC(Date.from(economicEventReq.getZonedDateTime().toInstant()))
                            .id(economicEventReq.getId())
                            .name(economicEventReq.getName())
                            .country(economicEventReq.getCountry())
                            .importance(economicEventReq.getImportance())
                            .actual(economicEventReq.getActual())
                            .forecast(economicEventReq.getForecast())
                            .previous(economicEventReq.getPrevious())
                            .build()
                            .toMongoData(dataId);
                })
                .collect(Collectors.toList()));
    }

    // TODO : 상수화

    public void saveDJI(DJIReq djiReq) {
        String _id = ZonedDateTime.now(ZoneId.of(Const.ZoneId.UTC)).format(Const.DateTimeFormat.yyyyMMddHH.getDateTimeFormatter());

        djiRepository.save(DJIRepository.DowJonesIndustrialAverage.builder()
                .price(djiReq.getPrice())
                .priceChange(djiReq.getPriceChange())
                .priceChangePercent(djiReq.getPriceChangePercent())
                .build()
                .toMongoData(_id));
    }

    public DJIRepository.DowJonesIndustrialAverage getDJI() {
        return djiRepository
                .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "_id")))
                .getContent()
                .get(0);
    }

    public void saveSPX(SPXReq spxReq) {
        String _id = ZonedDateTime.now(ZoneId.of(Const.ZoneId.UTC)).format(Const.DateTimeFormat.yyyyMMddHH.getDateTimeFormatter());

        spxRepository.save(SPXRepository.StandardAndPoor500.builder()
                .price(spxReq.getPrice())
                .priceChange(spxReq.getPriceChange())
                .priceChangePercent(spxReq.getPriceChangePercent())
                .build()
                .toMongoData(_id));
    }

    public SPXRepository.StandardAndPoor500 getSPX() {
        return spxRepository
                .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "_id")))
                .getContent()
                .get(0);
    }

    public void saveIXIC(IXICReq ixicReq) {
        String _id = ZonedDateTime.now(ZoneId.of(Const.ZoneId.UTC)).format(Const.DateTimeFormat.yyyyMMddHH.getDateTimeFormatter());

        ixicRepository.save(IXICRepository.NasdaqComposite.builder()
                .price(ixicReq.getPrice())
                .priceChange(ixicReq.getPriceChange())
                .priceChangePercent(ixicReq.getPriceChangePercent())
                .build()
                .toMongoData(_id));
    }

    public IXICRepository.NasdaqComposite getIXIC() {
        return ixicRepository
                .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "_id")))
                .getContent()
                .get(0);
    }
}
