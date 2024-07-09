package toy.slick.controller.vo;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiKey {
    @Email
    private String email;
}
