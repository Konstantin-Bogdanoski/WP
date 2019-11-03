package ukim.mk.finki.konstantin.bogdanoski.wp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */

@SpringBootApplication
@ServletComponentScan
public class WpApplication {

    public static void main(String[] args) {
        SpringApplication.run(WpApplication.class, args);
    }

}
