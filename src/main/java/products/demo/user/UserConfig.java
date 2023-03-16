package products.demo.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner createMockUsers(
            UserRepository repository) {
        return args -> {
            User aric = new User(
                    "aric", "aricRach", "aricrachmany@gmail.com", "");
            User tal = new User(
                    "tal", "talKabaso", "talkabaso@gmail.com", "");

            repository.saveAll(List.of(aric, tal));
        };
    }
}
