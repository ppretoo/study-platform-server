package tsikt.studyplatformserver;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin // aby sa k tomu neskôr vedel pripojiť JavaFX klient
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        repo.save(user);
    }
}
