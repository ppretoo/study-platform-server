package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin
public class StudyGroupController {

    private final StudyGroupRepository repo;

    public StudyGroupController(StudyGroupRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<StudyGroup> getAllGroups() {
        return repo.findAll();
    }

    @PostMapping
    public void createGroup(@RequestBody StudyGroup group) {
        repo.save(group);
    }
}
