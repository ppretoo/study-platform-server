package tsikt.studyplatformserver;

import java.util.List;
import tsikt.studyplatformserver.ActivityLogRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin
public class StudyGroupController {

    private final StudyGroupRepository repo;
    private final ActivityLogRepository activityRepo;

    public StudyGroupController(StudyGroupRepository repo, ActivityLogRepository activityRepo) {
        this.repo = repo;
        this.activityRepo = activityRepo;
    }

    @GetMapping
    public List<StudyGroup> getAllGroups() {
        return repo.findAll();
    }

    @PostMapping
    public void createGroup(@RequestBody StudyGroup group) {
        repo.save(group);
        activityRepo.log(null, null, "GROUP_CREATED", "Group: " + group.getName());
    }
}
