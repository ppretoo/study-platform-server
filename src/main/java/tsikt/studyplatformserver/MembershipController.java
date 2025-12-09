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
    public String addMembership(@RequestBody Membership membership) {

        Long userId = membership.getUserId();
        Long groupId = membership.getGroupId();
        String role = membership.getRole();

        if (repo.existsByUserAndGroup(userId, groupId)) {
            return "ALREADY_MEMBER";
        }

        repo.insert(userId, groupId, role);

        activityRepo.log(
                userId,
                groupId,
                "MEMBERSHIP_ADDED",
                "User joined group"
        );

        return "OK";
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
