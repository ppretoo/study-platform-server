package tsikt.studyplatformserver;

import java.util.List;
import tsikt.studyplatformserver.ActivityLogRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin
public class MembershipController {

    private final MembershipRepository repo;
    private final ActivityLogRepository activityRepo;

    public MembershipController(MembershipRepository repo, ActivityLogRepository activityRepo) {
        this.repo = repo;
        this.activityRepo = activityRepo;
    }

    @PostMapping
    public void addMembership(@RequestBody Membership membership) {
        if (membership.getRole() == null || membership.getRole().isBlank()) {
            membership.setRole("MEMBER");
        }
        repo.save(membership);

        activityRepo.log(
                membership.getUserId(),
                membership.getGroupId(),
                "MEMBERSHIP_ADDED",
                "Role: " + membership.getRole()
        );
    }
    @GetMapping("/by-group/{groupId}")
    public List<Membership> getByGroup(@PathVariable Long groupId) {
        return repo.findByGroupId(groupId);
    }

    @GetMapping("/by-user/{userId}")
    public List<Membership> getByUser(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }
}
