package ukim.mk.finki.konstantin.bogdanoski.wp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */

@SpringBootApplication
@ServletComponentScan
public class WpApplication {

    @Bean
    public Logger Logger() {
        return Logger.getLogger(WpApplication.class.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(WpApplication.class, args);
    }

}
