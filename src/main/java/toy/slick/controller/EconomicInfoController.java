package toy.slick.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.slick.common.Response;
import toy.slick.common.annotation.TimeLog;
import toy.slick.controller.vo.EconomicEvent;
import toy.slick.controller.vo.FearAndGreed;
import toy.slick.repository.mongo.EconomicEventRepository;
import toy.slick.repository.mongo.FearAndGreedRepository;
import toy.slick.service.EconomicInfoService;
import toy.slick.service.cacheable.EconomicInfoCacheableService;

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
    public Response<FearAndGreedRepository.FearAndGreed> getFearAndGreed() {
        return new Response<>(economicInfoService.findRecentFearAndGreed());
    }

    @TimeLog
    @PutMapping("/fearAndGreed")
    public Response<HttpStatus> putFearAndGreed(FearAndGreed fearAndGreed) {
        economicInfoService.saveFearAndGreed(fearAndGreed);

        return new Response<>(HttpStatus.OK);
    }

    @TimeLog
    @GetMapping("/economicEvent/list/{yyyy-MM-dd_UTC}")
    public Response<List<EconomicEventRepository.EconomicEvent>> getEconomicEventList(@PathVariable("yyyy-MM-dd_UTC") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return new Response<>(economicInfoCacheableService.findEconomicEventList(date));
    }

    @TimeLog
    @PutMapping("/economicEvent")
    public Response<HttpStatus> putEconomicEvent(EconomicEvent economicEvent) {
        economicInfoService.saveEconomicEvent(economicEvent);

        return new Response<>(HttpStatus.OK);
    }
}
