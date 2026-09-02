package br.com.acta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ActaPgApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActaPgApiApplication.class, args);
    }

}
