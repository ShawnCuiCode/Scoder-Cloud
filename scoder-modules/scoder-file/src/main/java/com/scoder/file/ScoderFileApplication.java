package com.scoder.file;

import com.scoder.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 *
 * @author Shawn Cui
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ScoderFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScoderFileApplication.class, args);
        System.out.println("                        .___               _____.__.__                     __                 __             .___\n" +
                "  ______ ____  ____   __| _/___________  _/ ____\\__|  |   ____     _______/  |______ ________/  |_  ____   __| _/\n" +
                " /  ___// ___\\/  _ \\ / __ |/ __ \\_  __ \\ \\   __\\|  |  | _/ __ \\   /  ___/\\   __\\__  \\\\_  __ \\   __\\/ __ \\ / __ | \n" +
                " \\___ \\\\  \\__(  <_> ) /_/ \\  ___/|  | \\/  |  |  |  |  |_\\  ___/   \\___ \\  |  |  / __ \\|  | \\/|  | \\  ___// /_/ | \n" +
                "/____  >\\___  >____/\\____ |\\___  >__|     |__|  |__|____/\\___  > /____  > |__| (____  /__|   |__|  \\___  >____ | \n" +
                "     \\/     \\/           \\/    \\/                            \\/       \\/            \\/                 \\/     \\/ ");
    }
}
