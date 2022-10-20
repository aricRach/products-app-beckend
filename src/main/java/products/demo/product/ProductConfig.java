package products.demo.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            ProductRepository repository) {
        return args -> {
            Product ball = new Product(
                    "ball", "https://picsum.photos/400?image=659", 56D, 5, 0D, "aricrachmany@gmail.com");
            Product shoes = new Product(
                    "shoes", "https://picsum.photos/400?image=261", 320D, 2, 0D, "aricrachmany@gmail.com");
            Product phone = new Product(
                    "phone", "https://picsum.photos/400?image=340", 1111D, 55, 0D, "aricrachmany@gmail.com");
            Product watch = new Product(
                    "shoes", "https://picsum.photos/400?image=924", 370D, 9, 20D, "aricrachmany@gmail.com");
            repository.saveAll(List.of(ball, shoes, phone, watch));
        };
    }
}
