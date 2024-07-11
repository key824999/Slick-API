package toy.slick.controller.vo.response;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Builder
@Getter
public class EconomicEventRes {
    private ZonedDateTime zonedDateTime;
    private String id;
    private String name;
    private String country;
    private String importance;
    private String actual;
    private String forecast;
    private String previous;
}
