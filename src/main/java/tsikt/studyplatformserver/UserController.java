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
/*
    @PostMapping
    public void createUser(@RequestBody User user) {
        repo.save(user);
        activityRepo.log(null, null, "USER_CREATED", "User: " + user.getEmail());
    }
 */

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return repo.findById(id);
    }

    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam String email) {
        return repo.findByEmail(email);
    }

    @PutMapping("/{id}")
    public User updateProfile(@PathVariable Long id,
                              @RequestBody UpdateProfileRequest req) {

        // jednoduché validácie
        if (req.getName() == null || req.getName().isBlank()
                || req.getEmail() == null || req.getEmail().isBlank()) {
            throw new IllegalArgumentException("Name and email must not be empty");
        }

        repo.updateProfile(id, req.getName(), req.getEmail());

        // logovanie aktivity
        activityRepo.log(id, null,
                "PROFILE_UPDATED",
                "User updated profile: " + req.getEmail());

        // vrátime aktualizovaného používateľa
        return repo.findById(id);
    }

    public static class UpdateProfileRequest {
        private String name;
        private String email;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}