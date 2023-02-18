package products.demo.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import products.demo.user.User;

import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner createMockProducts(
            ProductRepository repository) {
        User aric = new User("aric", "aricRach", "aricrachmany@gmail.com");
        User Tal = new User("tal", "talKab", "talkabaso@gmail.com");
        return args -> {
            Product ball = new Product(
                    "ball", "https://picsum.photos/400?image=659", 56D, 5, 0D, aric);
            Product shoes = new Product(
                    "shoes", "https://picsum.photos/400?image=261", 320D, 2, 0D, aric);
            Product phone = new Product(
                    "phone", "https://picsum.photos/400?image=340", 1111D, 55, 0D, Tal);
            Product watch = new Product(
                    "mirror", "https://picsum.photos/400?image=924", 370D, 9, 20D, Tal);
            Product phone2 = new Product(
                    "phone", "https://picsum.photos/400?image=340", 1111D, 55, 0D, Tal);
            Product watch2 = new Product(
                    "mirror", "https://picsum.photos/400?image=924", 370D, 9, 20D, Tal);
            Product phone3 = new Product(
                    "phone", "https://picsum.photos/400?image=340", 1111D, 55, 0D, Tal);
            Product watch3 = new Product(
                    "mirror", "https://picsum.photos/400?image=924", 370D, 9, 20D, Tal);
            repository.saveAll(List.of(ball, shoes, phone, watch, watch2, phone2, watch3, phone3));
        };
    }
}
