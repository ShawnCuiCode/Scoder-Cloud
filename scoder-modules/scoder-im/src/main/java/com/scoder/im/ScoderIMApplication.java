package com.scoder.im;


import com.scoder.common.security.annotation.EnableCustomConfig;
import com.scoder.common.security.annotation.EnableScoderFeignClients;
import com.scoder.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Scoder IM Application.
 * <p>
 * This class initializes the application by enabling custom configurations, Swagger API documentation,
 * and Feign clients for service-to-service communication.
 * <p>
 * The application runs as a Spring Boot application.
 *
 * @author Shawn Cui
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableScoderFeignClients
@SpringBootApplication
public class ScoderIMApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScoderIMApplication.class, args);
        System.out.println("                        .___             .__                    __                 __             .___\n" +
                "  ______ ____  ____   __| _/___________  |__| _____     _______/  |______ ________/  |_  ____   __| _/\n" +
                " /  ___// ___\\/  _ \\ / __ |/ __ \\_  __ \\ |  |/     \\   /  ___/\\   __\\__  \\\\_  __ \\   __\\/ __ \\ / __ | \n" +
                " \\___ \\\\  \\__(  <_> ) /_/ \\  ___/|  | \\/ |  |  Y Y  \\  \\___ \\  |  |  / __ \\|  | \\/|  | \\  ___// /_/ | \n" +
                "/____  >\\___  >____/\\____ |\\___  >__|    |__|__|_|  / /____  > |__| (____  /__|   |__|  \\___  >____ | \n" +
                "     \\/     \\/           \\/    \\/                 \\/       \\/            \\/                 \\/     \\/ ");
    }

}
