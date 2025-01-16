package com.scoder.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * ScoderGatewayApplication
 * <p>
 * Main entry point for the Scoder Gateway service.
 * This service acts as a gateway for routing and managing requests to different microservices
 * within the Scoder application ecosystem.
 * <p>
 * Features:
 * - Centralized request routing
 * - Load balancing
 * - Rate limiting and authorization
 * - Custom exception handling
 *
 * @author Shawn Cui
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ScoderGatewayApplication {

    /**
     * Main method to launch the Scoder Gateway service.
     *
     * @param args Command-line arguments for the application
     */
    public static void main(String[] args) {
        SpringApplication.run(ScoderGatewayApplication.class, args);

        // Display a custom startup banner in the console
        System.out.println("                        .___                            __                                          __                 __             .___\n" +
                "  ______ ____  ____   __| _/___________     _________ _/  |_  ______  _  _______  ___.__.   _______/  |______ ________/  |_  ____   __| _/\n" +
                " /  ___// ___\\/  _ \\ / __ |/ __ \\_  __ \\   / ___\\__  \\\\   __\\/ __ \\ \\/ \\/ /\\__  \\<   |  |  /  ___/\\   __\\__  \\\\_  __ \\   __\\/ __ \\ / __ | \n" +
                " \\___ \\\\  \\__(  <_> ) /_/ \\  ___/|  | \\/  / /_/  > __ \\|  | \\  ___/\\     /  / __ \\\\___  |  \\___ \\  |  |  / __ \\|  | \\/|  | \\  ___// /_/ | \n" +
                "/____  >\\___  >____/\\____ |\\___  >__|     \\___  (____  /__|  \\___  >\\/\\_/  (____  / ____| /____  > |__| (____  /__|   |__|  \\___  >____ | \n" +
                "     \\/     \\/           \\/    \\/        /_____/     \\/          \\/             \\/\\/           \\/            \\/                 \\/     \\/ ");
    }
}