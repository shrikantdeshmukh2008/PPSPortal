package mff.oms.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FfOmsIntegrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FfOmsIntegrationServiceApplication.class, args);
	}

}
