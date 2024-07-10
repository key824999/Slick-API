package toy.slick.controller.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class EconomicEvent {
    private ZonedDateTime zonedDateTime;
    private String id;
    private String name;
    private String country;
    private String importance;
    private String actual;
    private String forecast;
    private String previous;
}
