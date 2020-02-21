package pathsala.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PathsalaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PathsalaBackendApplication.class, args);
    }

    @EventListener
    public void onReady(ApplicationReadyEvent readyEvent) {

    }

}
