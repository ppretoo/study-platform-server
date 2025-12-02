package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin
public class ResourceController {

    private final ResourceRepository repo;

    public ResourceController(ResourceRepository repo) {
        this.repo = repo;
    }

    // všetky materiály pre danú skupinu
    @GetMapping("/by-group/{groupId}")
    public List<Resource> getResourcesByGroup(@PathVariable Long groupId) {
        return repo.findByGroupId(groupId);
    }

    // pridanie nového materiálu
    @PostMapping
    public void createResource(@RequestBody Resource resource) {
        repo.save(resource);
    }
}
