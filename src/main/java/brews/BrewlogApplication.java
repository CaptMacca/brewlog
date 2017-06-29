package brews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class BrewlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrewlogApplication.class, args);
	}
}
