package mii.mcc72.ams_server_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AmsServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmsServerAppApplication.class, args);
                System.out.println("============================");
		System.out.println("Ams-Server-App is running...");
	}
}
