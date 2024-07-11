package toy.slick.controller.vo.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyRes {
    private String apiKey;
    private String email;
    private String expiredZonedDateTime;
}
