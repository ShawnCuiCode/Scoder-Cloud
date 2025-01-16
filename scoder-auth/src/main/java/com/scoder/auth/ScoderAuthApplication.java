package com.scoder.auth;

import com.scoder.common.security.annotation.EnableScoderFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * The main entry point for the Scoder Auth application.
 * <p>
 * This application is responsible for authentication services, including login, logout, and token management.
 * <p>
 * Key Features:
 * - Excludes DataSourceAutoConfiguration as this module does not interact with a database directly.
 * - Uses Feign clients for inter-service communication.
 *
 * @author Shawn Cui
 */
@EnableScoderFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ScoderAuthApplication {

    /**
     * Main method to launch the Scoder Auth application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ScoderAuthApplication.class, args);
        System.out.println("                        .___                            __  .__                __                 __             .___\n" +
                "  ______ ____  ____   __| _/___________  _____   __ ___/  |_|  |__     _______/  |______ ________/  |_  ____   __| _/\n" +
                " /  ___// ___\\/  _ \\ / __ |/ __ \\_  __ \\ \\__  \\ |  |  \\   __\\  |  \\   /  ___/\\   __\\__  \\\\_  __ \\   __\\/ __ \\ / __ | \n" +
                " \\___ \\\\  \\__(  <_> ) /_/ \\  ___/|  | \\/  / __ \\|  |  /|  | |   Y  \\  \\___ \\  |  |  / __ \\|  | \\/|  | \\  ___// /_/ | \n" +
                "/____  >\\___  >____/\\____ |\\___  >__|    (____  /____/ |__| |___|  / /____  > |__| (____  /__|   |__|  \\___  >____ | \n" +
                "     \\/     \\/           \\/    \\/             \\/                 \\/       \\/            \\/                 \\/     \\/ ");
    }
}