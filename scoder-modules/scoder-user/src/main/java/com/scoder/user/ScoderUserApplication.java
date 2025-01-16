package com.scoder.user;

import com.scoder.common.security.annotation.EnableCustomConfig;
import com.scoder.common.security.annotation.EnableScoderFeignClients;
import com.scoder.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Scoder User module.
 * This application is responsible for managing user-related operations and integrates various features, such as:
 * - Custom configuration with {@link EnableCustomConfig}.
 * - Swagger documentation with {@link EnableCustomSwagger2}.
 * - Feign client support for communication between microservices with {@link EnableScoderFeignClients}.
 * <p>
 * Features include:
 * - User registration, authentication, and management.
 * - Integration with shared libraries for security and configuration.
 * - Exposure of RESTful APIs for user-related operations.
 * <p>
 * To run the application:
 * Execute the `main` method, which starts the embedded server and initializes the application context.
 *
 * @author Shawn Cui
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableScoderFeignClients
@SpringBootApplication
public class ScoderUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScoderUserApplication.class, args);
        System.out.println("                        .___                                                   __                 __             .___\n" +
                "  ______ ____  ____   __| _/___________   __ __  ______ ___________    _______/  |______ ________/  |_  ____   __| _/\n" +
                " /  ___// ___\\/  _ \\ / __ |/ __ \\_  __ \\ |  |  \\/  ___// __ \\_  __ \\  /  ___/\\   __\\__  \\\\_  __ \\   __\\/ __ \\ / __ | \n" +
                " \\___ \\\\  \\__(  <_> ) /_/ \\  ___/|  | \\/ |  |  /\\___ \\\\  ___/|  | \\/  \\___ \\  |  |  / __ \\|  | \\/|  | \\  ___// /_/ | \n" +
                "/____  >\\___  >____/\\____ |\\___  >__|    |____//____  >\\___  >__|    /____  > |__| (____  /__|   |__|  \\___  >____ | \n" +
                "     \\/     \\/           \\/    \\/                   \\/     \\/             \\/            \\/                 \\/     \\/ ");
    }
}