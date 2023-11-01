package ee.lhv.aml;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
	info = @Info(
		title = "LHV AML API",
		version = "1.0",
		description = "Service to validate if the person is sanctioned by EU banks"
	)
)
public class AmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmlApplication.class, args);
	}

}
