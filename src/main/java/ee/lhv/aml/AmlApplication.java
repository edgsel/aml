package ee.lhv.aml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmlApplication.class, args);
	}

}
