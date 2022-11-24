package products.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public void addNewUser(User user) {
       Optional<User> User = userRepository.findUserByEmail(user.getEmail());
        if(!User.isPresent()) {
            this.userRepository.save(user);
        }
    }
}
