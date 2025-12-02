package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin
public class MembershipController {

    private final MembershipRepository repo;

    public MembershipController(MembershipRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public void addMembership(@RequestBody Membership membership) {
        if (membership.getRole() == null || membership.getRole().isBlank()) {
            membership.setRole("MEMBER");
        }
        repo.save(membership);
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
