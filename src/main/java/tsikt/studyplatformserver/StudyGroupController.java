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
        repo.createGroup(group.getName(), group.getDescription());
        activityRepo.log(null, null, "GROUP_CREATED","Group: " + group.getName());
    }

    @PutMapping("/{id}")
    public void updateGroup(@PathVariable Long id, @RequestBody StudyGroup group) {
        repo.updateGroup(id, group.getName(), group.getDescription());
        activityRepo.log(null, id, "GROUP_UPDATED",
                "Group: " + group.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        repo.deleteGroup(id);
        activityRepo.log(null, id, "GROUP_DELETED",
                "Group id: " + id);
    }

}
