package toy.slick.controller.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DJIReq {
    private String price;
    private String priceChange;
    private String priceChangePercent;
}
