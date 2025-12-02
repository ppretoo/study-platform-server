package tsikt.studyplatformserver;

import org.springframework.web.bind.annotation.*;
import tsikt.studyplatformserver.ActivityLogRepository;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserRepository repo;
    private final ActivityLogRepository activityRepo;

    public UserController(UserRepository repo, ActivityLogRepository activityRepo) {
        this.repo = repo;
        this.activityRepo = activityRepo;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        repo.save(user);
        activityRepo.log(null, null, "USER_CREATED", "User: " + user.getEmail());
    }
}