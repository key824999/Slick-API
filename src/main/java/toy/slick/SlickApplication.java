package toy.slick;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import toy.slick.common.Const;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class SlickApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(Const.ZoneId.UTC));
	}

	public static void main(String[] args) {
		SpringApplication.run(SlickApplication.class, args);
	}

}
