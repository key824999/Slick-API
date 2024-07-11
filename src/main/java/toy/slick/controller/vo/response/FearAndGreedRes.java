package toy.slick.controller.vo.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FearAndGreedRes {
    private String rating;
    private double score;
}
