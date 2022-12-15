package products.demo.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return this.productService.getProducts();
    }

    @GetMapping(path = "{email}")
    public List<Product> getProductsByOwner(@PathVariable("email") String email) {
        return this.productService.getProductsByOwner(email);
    }

    @PostMapping
    public void registerProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @DeleteMapping(path="{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping(path="{productId}")
    public void updateProduct(
            @PathVariable("productId") Long productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price) {
        productService.updateProduct(productId, name, price);
    }

    @PutMapping(path="update-product/{productId}")
    public void updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody() Product product) {
        productService.updateProduct(product, productId);
    }

    @PostMapping(path = "buy")
    public void buyProducts(@RequestBody Cart cart)
    {
        try {
            HashMap stockStatus = productService.checkStock(cart.cartItems);
            // todo decide if map of errors is needed
            productService.updatePurchase(cart);
        } catch (Exception e) {
            throw e;
        }

    }
}
