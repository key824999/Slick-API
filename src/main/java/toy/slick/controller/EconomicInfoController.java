package toy.slick.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.slick.common.Const;
import toy.slick.common.Response;
import toy.slick.common.annotation.TimeLog;
import toy.slick.controller.vo.request.DJIReq;
import toy.slick.controller.vo.request.EconomicEventReq;
import toy.slick.controller.vo.request.FearAndGreedReq;
import toy.slick.controller.vo.response.DJIRes;
import toy.slick.controller.vo.response.EconomicEventRes;
import toy.slick.controller.vo.response.FearAndGreedRes;
import toy.slick.repository.mongo.DJIRepository;
import toy.slick.repository.mongo.EconomicEventRepository;
import toy.slick.repository.mongo.FearAndGreedRepository;
import toy.slick.service.EconomicInfoService;
import toy.slick.service.cacheable.EconomicInfoCacheableService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/economicInfo")
public class EconomicInfoController {
    private final EconomicInfoService economicInfoService;
    private final EconomicInfoCacheableService economicInfoCacheableService;

    public EconomicInfoController(EconomicInfoService economicInfoService,
                                  EconomicInfoCacheableService economicInfoCacheableService) {
        this.economicInfoService = economicInfoService;
        this.economicInfoCacheableService = economicInfoCacheableService;
    }

    @TimeLog
    @GetMapping("/fearAndGreed")
    public Response<FearAndGreedRes> getFearAndGreed() {
        FearAndGreedRepository.FearAndGreed fearAndGreed = economicInfoService.findRecentFearAndGreed();

        return new Response<>(FearAndGreedRes.builder()
                .rating(fearAndGreed.getRating())
                .score(fearAndGreed.getScore())
                .build());
    }

    @TimeLog
    @PutMapping("/fearAndGreed")
    public Response<HttpStatus> putFearAndGreed(@RequestBody FearAndGreedReq fearAndGreedReq) {
        economicInfoService.saveFearAndGreed(fearAndGreedReq);

        return new Response<>(HttpStatus.OK);
    }

    @TimeLog
    @GetMapping("/economicEvent/list/{yyyy-MM-dd_UTC}")
    public Response<List<EconomicEventRes>> getEconomicEventList(@PathVariable("yyyy-MM-dd_UTC") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<EconomicEventRepository.EconomicEvent> economicEventList = economicInfoCacheableService.findEconomicEventList(date);

        return new Response<>(economicEventList
                .stream()
                .map(economicEvent -> EconomicEventRes.builder()
                        .actual(economicEvent.getActual())
                        .forecast(economicEvent.getForecast())
                        .previous(economicEvent.getPrevious())
                        .id(economicEvent.getId())
                        .country(economicEvent.getCountry())
                        .importance(economicEvent.getImportance())
                        .name(economicEvent.getName())
                        .zonedDateTime(ZonedDateTime.ofInstant(economicEvent.getDateUTC().toInstant(), ZoneId.of(Const.ZoneId.UTC)))
                        .build())
                .toList());
    }

    @TimeLog
    @PutMapping("/economicEvent")
    public Response<HttpStatus> putEconomicEvent(@RequestBody EconomicEventReq economicEventReq) {
        economicInfoService.saveEconomicEvent(economicEventReq);

        return new Response<>(HttpStatus.OK);
    }

    @TimeLog
    @PutMapping("/economicEvent/list")
    public Response<HttpStatus> putEconomicEventList(@RequestBody List<EconomicEventReq> economicEventReqList) {
        economicInfoService.saveEconomicEventList(economicEventReqList);

        return new Response<>(HttpStatus.OK);
    }

    @TimeLog
    @PutMapping("/DJI")
    public Response<HttpStatus> putDJI(@RequestBody DJIReq djiReq) {
        economicInfoService.saveDJI(djiReq);

        return new Response<>(HttpStatus.OK);
    }

    @TimeLog
    @GetMapping("/DJI")
    public Response<DJIRes> getDJI() {
        DJIRepository.DowJonesIndustrialAverage DJI = economicInfoCacheableService.getDJI();

        return new Response<>(DJIRes.builder()
                .price(DJI.getPrice())
                .priceChange(DJI.getPriceChange())
                .priceChangePercent(DJI.getPriceChangePercent())
                .build());
    }
}
