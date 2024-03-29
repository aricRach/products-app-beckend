package products.demo.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import products.demo.user.User;
import products.demo.user.UserRepository;

import java.util.*;

@Service
@Slf4j

public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    public List<Product> getAllProducts(String userEmail) {
        if (userEmail != null) {
            return productRepository.findAllExceptUser(userEmail);
        }
        return productRepository.findAll();
    }
@Transactional
    public Product addNewProduct(Product product) {
        if(this.isProductValid(product)) {
            Optional<User> OptionalUser = userRepository.findUserByEmail(product.getOwner());
            if(OptionalUser.isPresent()) {
                User productUser = OptionalUser.get();
                productUser.addProduct(product);
                product.setUserOwner(productUser);
                return product;
            } else {
                throw new IllegalMonitorStateException("user not exist");
            }
        } else {
            throw new IllegalMonitorStateException("product not valid");
        }
    }

    public void deleteProduct(Long id) {
        boolean isExist = productRepository.existsById(id);
        if(!isExist) {
            throw new IllegalMonitorStateException("product with id " + id + " doesn't exist");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateProduct(Long productId, String name, Double price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalStateException("product with id " + productId + "does not exist") );

        if(name != null && name.length() > 0 && !Objects.equals(product.getName(), name)) {
            product.setName(name);
        }
        if(price != null && price > 0) {
            product.setPrice(price);
        }
    }

    @Transactional
    public void updateProduct(Product product, final Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalStateException("product with id " + productId + "does not exist") );

        if(this.isProductValid(product) && Objects.equals(existingProduct.getOwner(), product.getOwner())) {
            existingProduct.updateProduct(product);
        } else {
            throw new IllegalStateException("product not valid");
        }
    }

    private boolean isProductValid(Product product) {
        return product.getName() != null &&
                product.getOwner() != null &&
                product.getStock() >= 0 &&
                product.getPrice() > 0 &&
                product.getDiscountPercent() >= 0 && product.getDiscountPercent() < 100;
    }

    public HashMap checkStock(ProductCartItem[] products) {
        HashMap<String, Boolean> map = new HashMap();
        Boolean isValid = true;
        String validationMessage = "";
        for(int i=0; i<products.length; i++) {
            ProductCartItem currentProduct = products[i];
            Optional<Product> productInDb = productRepository.findProductById(currentProduct.getId());
            if(currentProduct.getNumberOfItems() > productInDb.get().getStock()) {
                map.put(currentProduct.getName(), false);
                isValid = false;
                validationMessage += ' ' + currentProduct.getName();
            } else {
                map.put(currentProduct.getName(), true);
            }
            if(!isValid) {
                throw new IllegalStateException("The following are out of stock for your order:\n " + validationMessage);
//                throw new IllegalStateException("The following are not in stock for your order:\n " + map);
            }
        }
        return map;
    }

    @Transactional
    public void updatePurchase(Cart cart) {
        ProductCartItem[] products = cart.cartItems;
        log.info("" + products.length);
        for(int i=0; i<products.length; i++) {
            ProductCartItem currentCartItemProduct = products[i];
            Optional<Product> optionalProductInDb = productRepository.findProductById(currentCartItemProduct.getId());
            if(optionalProductInDb.isPresent()) {
                Product currentProduct = optionalProductInDb.get();
                currentProduct.setStock(currentCartItemProduct.getStock() - currentCartItemProduct.getNumberOfItems());
                this.setUserOrdersHistory(currentProduct, currentCartItemProduct.getNumberOfItems(), cart.getBuyerEmail());
            }
        }
    }

    @Transactional
    public void setUserOrdersHistory(Product product, int numberOfItems, String buyerEmail) {
        ProductPurchaseItem purchaseItem = new ProductPurchaseItem(product);
        purchaseItem.setDate(new Date());
        purchaseItem.setNumberOfItems(numberOfItems);
        userRepository.findUserByEmail(buyerEmail).get().addProductToOrdersHistory(purchaseItem);
    }

    public List<Product> getProductsByOwner(String email) {
       return this.productRepository.findProductsByOwner(email);
    }

    public Optional<Product> getProductById(Long pid) {
        return productRepository.findProductById(pid);
    }

    @Transactional
    public void addImageUrl(Long productId, String downloadURL) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalStateException("product with id " + productId + "does not exist") );
        existingProduct.setImage(downloadURL);
    }
}
