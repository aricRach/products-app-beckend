package products.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import products.demo.product.ProductPurchaseItem;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        log.info(email);
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/orders/{email}")
    public List<ProductPurchaseItem> geUserOrdersByEmail(@PathVariable String email) {
        log.info(email);
        return this.userService.getUserOrdersByEmail(email);
    }

    @PostMapping
    public void registerUser(@RequestBody User user) {
        log.info(user.toString());
        this.userService.addNewUser(user);
    }

}
