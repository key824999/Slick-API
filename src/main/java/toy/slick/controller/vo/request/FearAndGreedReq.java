package toy.slick.controller.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FearAndGreedReq {
    private String rating;
    private double score;
}
