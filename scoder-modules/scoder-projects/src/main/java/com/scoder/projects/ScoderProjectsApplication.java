package com.scoder.projects;

import com.scoder.common.security.annotation.EnableCustomConfig;
import com.scoder.common.security.annotation.EnableScoderFeignClients;
import com.scoder.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Scoder Projects microservice.
 * This class initializes and starts the application.
 * <p>
 * Annotations are used to enable additional configurations such as security, Swagger, and Feign clients.
 *
 * @author Shawn Cui
 */
@EnableCustomConfig // Enables custom security configurations
@EnableCustomSwagger2 // Enables Swagger documentation for API endpoints
@EnableScoderFeignClients // Enables Feign clients for inter-service communication
@SpringBootApplication // Marks this class as the main Spring Boot application
public class ScoderProjectsApplication {

    /**
     * The main method serves as the entry point for the Scoder Projects microservice.
     *
     * @param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        // Starts the Spring Boot application
        SpringApplication.run(ScoderProjectsApplication.class, args);

        // Prints an ASCII art banner upon successful startup
        System.out.println("                        .___                                     __               __                     __                 __             .___\n" +
                "  ______ ____  ____   __| _/___________  _____________  ____    |__| ____   _____/  |_  ______   _______/  |______ ________/  |_  ____   __| _/\n" +
                " /  ___// ___\\/  _ \\ / __ |/ __ \\_  __ \\ \\____ \\_  __ \\/  _ \\   |  |/ __ \\_/ ___\\   __\\/  ___/  /  ___/\\   __\\__  \\\\_  __ \\   __\\/ __ \\ / __ | \n" +
                " \\___ \\\\  \\__(  <_> ) /_/ \\  ___/|  | \\/ |  |_> >  | \\(  <_> )  |  \\  ___/\\  \\___|  |  \\___ \\   \\___ \\  |  |  / __ \\|  | \\/|  | \\  ___// /_/ | \n" +
                "/____  >\\___  >____/\\____ |\\___  >__|    |   __/|__|   \\____/\\__|  |\\___  >\\___  >__| /____  > /____  > |__| (____  /__|   |__|  \\___  >____ | \n" +
                "     \\/     \\/           \\/    \\/        |__|               \\______|    \\/     \\/          \\/       \\/            \\/                 \\/     \\/ ");
    }
}