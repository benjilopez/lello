package ec.blopez.lello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Benjamin Lopez on 12.01.17.
 */
@EnableScheduling
@SpringBootApplication
public class LelloApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LelloApplication.class);
    }
}
