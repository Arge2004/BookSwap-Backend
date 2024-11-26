package BookSwap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableWebSecurity
public class BookSwapApplication {

	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if(port != null) {
			System.setProperty("server.port", port);
		}
		SpringApplication.run(BookSwapApplication.class, args);
	}
}