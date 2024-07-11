package toy.slick.controller.vo.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SPXRes {
    private String price;
    private String priceChange;
    private String priceChangePercent;
}
