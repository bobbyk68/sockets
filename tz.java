import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class TimeZoneCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeZoneCheckApplication.class, args);
    }

    // Custom CommandLineRunner to check the time zone
    @Bean
    public CommandLineRunner checkTimeZone() {
        return args -> {
            TimeZone timeZone = TimeZone.getDefault();
            ZoneId expectedZone = ZoneId.of("UTC");
            
            if (!timeZone.toZoneId().equals(expectedZone)) {
                System.err.println("The application is not running in UTC time zone. Shutting down...");
                SpringApplication.exit(SpringApplication.run(TimeZoneCheckApplication.class, args), () -> 1);
            } else {
                System.out.println("Application running in UTC time zone.");
            }
        };
    }
}