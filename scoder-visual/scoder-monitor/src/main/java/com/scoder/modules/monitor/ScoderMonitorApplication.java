package com.scoder.modules.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Shawn Cui
 */
@EnableAdminServer
@SpringBootApplication
public class ScoderMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScoderMonitorApplication.class, args);
        System.out.println("                        .___                                   .__  __                           __                 __             .___\n" +
                "  ______ ____  ____   __| _/___________    _____   ____   ____ |__|/  |_  ___________    _______/  |______ ________/  |_  ____   __| _/\n" +
                " /  ___// ___\\/  _ \\ / __ |/ __ \\_  __ \\  /     \\ /  _ \\ /    \\|  \\   __\\/  _ \\_  __ \\  /  ___/\\   __\\__  \\\\_  __ \\   __\\/ __ \\ / __ | \n" +
                " \\___ \\\\  \\__(  <_> ) /_/ \\  ___/|  | \\/ |  Y Y  (  <_> )   |  \\  ||  | (  <_> )  | \\/  \\___ \\  |  |  / __ \\|  | \\/|  | \\  ___// /_/ | \n" +
                "/____  >\\___  >____/\\____ |\\___  >__|    |__|_|  /\\____/|___|  /__||__|  \\____/|__|    /____  > |__| (____  /__|   |__|  \\___  >____ | \n" +
                "     \\/     \\/           \\/    \\/              \\/            \\/                             \\/            \\/                 \\/     \\/ ");
    }
}
