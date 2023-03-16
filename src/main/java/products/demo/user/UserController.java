package products.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import products.demo.TokenManagment.TokenManagementService;
import products.demo.product.ProductPurchaseItem;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="api/v1/user")
public class UserController {

    private final UserService userService;
    private final TokenManagementService tokenManagementService;

    @Autowired
    public UserController(UserService userService, TokenManagementService tokenManagementService) {
        this.userService = userService;
        this.tokenManagementService = tokenManagementService;
    }

    @GetMapping
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/orders/{email}")
    public List<ProductPurchaseItem> geUserOrdersByEmail(@PathVariable String email,
    @RequestHeader("Authorization") String authorization) {
        try {
            this.tokenManagementService.checkTokenByUserEmail(authorization, email);
            return this.userService.getUserOrdersByEmail(email);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping
    public void registerUser(@RequestBody User user) {
        this.userService.addNewUser(user);
    }

    @PostMapping("set-token")
    public void setUserToken(@RequestBody User user) {
        this.userService.setToken(user);
    }
}
