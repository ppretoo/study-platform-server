package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity")
@CrossOrigin
public class ActivityLogController {

    private final ActivityLogRepository repo;

    public ActivityLogController(ActivityLogRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ActivityLog> getAll() {
        return repo.findAll();
    }

    @GetMapping("/by-group/{groupId}")
    public List<ActivityLog> getByGroup(@PathVariable Long groupId) {
        return repo.findByGroupId(groupId);
    }

    @GetMapping("/by-user/{userId}")
    public List<ActivityLog> getByUser(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }
}
