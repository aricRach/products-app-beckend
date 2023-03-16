package products.demo.TokenManagment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import products.demo.product.Product;
import products.demo.product.ProductRepository;
import products.demo.user.User;
import products.demo.user.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class TokenManagementService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public TokenManagementService(ProductRepository productRepository,
                                  UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void checkTokenByUserEmail(String token, String userId) {
        Optional<User> optionalUser = this.userRepository.findUserByEmail(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean isAuthenticated = Objects.equals(user.getToken(), token);
            if(!isAuthenticated) {
                throw new IllegalStateException("invalid token");
            }
        } else {
            throw new IllegalStateException("user not exist in DB");
        }
    }

    public void checkTokenByProductId(String token, Long productId) {
        Optional<Product> optionalProduct = this.productRepository.findProductById(productId);
        if(optionalProduct.isPresent()) {
            this.checkTokenByUserEmail(token, optionalProduct.get().getOwner());
        } else {
            throw new IllegalStateException("product not exist in DB");
        }
    }
}
