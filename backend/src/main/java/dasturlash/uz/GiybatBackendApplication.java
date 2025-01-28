package dasturlash.uz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GiybatBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiybatBackendApplication.class, args);
        log.info("Giybat backend application started");
    }

}
