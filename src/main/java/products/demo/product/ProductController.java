package products.demo.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import products.demo.TokenManagment.TokenManagementService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final TokenManagementService tokenManagementService;

    @Autowired
    public ProductController(ProductService productService, TokenManagementService tokenManagementService) {
        this.productService = productService;
        this.tokenManagementService = tokenManagementService;
    }

    @GetMapping()
    public List<Product> getAllProducts(@RequestParam("userEmail") String userEmail) {
        if(userEmail != null) {
            return this.productService.getAllProducts(userEmail);
        }
        return this.productService.getAllProducts(null);
    }


    @GetMapping(path = "{email}")
    public List<Product> getProductsByOwner(@PathVariable("email") String email) {
        return this.productService.getProductsByOwner(email);
    }

    @GetMapping(path = "single-product/{pid}")
    public Optional<Product> getProductById(@PathVariable("pid") Long pid) {
        return this.productService.getProductById(pid);
    }

    @PostMapping
    public Product registerProduct(@RequestBody Product product, @RequestHeader("Authorization") String authorization) {
        try {
            this.tokenManagementService.checkTokenByUserEmail(authorization, product.getOwner());
            return productService.addNewProduct(product);
        }
        catch (Exception e) {
            throw (e);
        }
    }

    @DeleteMapping(path="{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId, @RequestHeader("Authorization") String authorization) {
        try {
            this.tokenManagementService.checkTokenByProductId(authorization, productId);
            productService.deleteProduct(productId);
        } catch (Exception e) {
            throw (e);
        }
    }

    @PutMapping(path="{productId}")
    public void updateProduct(
            @PathVariable("productId") Long productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestHeader("Authorization") String authorization) {
        try {
            this.tokenManagementService.checkTokenByProductId(authorization, productId);
            productService.updateProduct(productId, name, price);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @PutMapping(path="update-product/{productId}")
    public void updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody() Product product,
            @RequestHeader("Authorization") String authorization) {
        try{
            this.tokenManagementService.checkTokenByProductId(authorization, productId);
            productService.updateProduct(product, productId);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @PatchMapping(path="add-image-url/{productId}")
    public void addImageUrlToProduct(
            @PathVariable("productId") Long productId,
            @RequestBody() String downloadURL,
            @RequestHeader("Authorization") String authorization) {
        try {
            this.tokenManagementService.checkTokenByProductId(authorization, productId);
            productService.addImageUrl(productId, downloadURL);
        }
        catch (Exception e) {
         throw e;
        }
    }

    @PostMapping(path = "buy")
    public void buyProducts(@RequestBody Cart cart,
                            @RequestHeader("Authorization") String authorization) {
        try {
            this.tokenManagementService.checkTokenByUserEmail(authorization, cart.getBuyerEmail());
            HashMap stockStatus = productService.checkStock(cart.cartItems);
            // todo decide if map of errors is needed
            productService.updatePurchase(cart);
        } catch (Exception e) {
            throw e;
        }
    }
}
