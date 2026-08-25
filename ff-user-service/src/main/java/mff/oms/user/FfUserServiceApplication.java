package mff.oms.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class FfUserServiceApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(FfUserServiceApplication.class, args);
	}

	@PostConstruct
	public void checkDatasource() {
		System.out.println("Datasource URL: " + environment.getProperty("spring.datasource.url"));
	}

}
