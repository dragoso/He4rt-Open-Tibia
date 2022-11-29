package he4rt.open.tibia.world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class }, scanBasePackages = "he4rt")
@EnableJpaRepositories(basePackages = "he4rt")
@EntityScan(basePackages = "he4rt")
public class HotWorldApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
        SpringApplication.run(HotWorldApplication.class, args);
    }

}
