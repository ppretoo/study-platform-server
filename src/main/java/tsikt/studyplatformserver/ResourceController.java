package tsikt.studyplatformserver;

import java.util.List;
import tsikt.studyplatformserver.ActivityLogRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin
public class ResourceController {

    private final ResourceRepository repo;
    private final ActivityLogRepository activityRepo;

    public ResourceController(ResourceRepository repo, ActivityLogRepository activityRepo) {
        this.repo = repo;
        this.activityRepo = activityRepo;
    }

    @GetMapping("/by-group/{groupId}")
    public List<Resource> getResourcesByGroup(@PathVariable Long groupId) {
        return repo.findByGroupId(groupId);
    }

    @PostMapping
    public void createResource(@RequestBody Resource resource) {
        repo.save(resource);
        activityRepo.log(
                resource.getUploadedBy(),
                resource.getGroupId(),
                "RESOURCE_ADDED",
                "Resource: " + resource.getName()
        );
    }
}
