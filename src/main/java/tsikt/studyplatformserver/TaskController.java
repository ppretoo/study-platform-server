package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    // všetky úlohy pre konkrétnu skupinu
    @GetMapping("/by-group/{groupId}")
    public List<Task> getTasksByGroup(@PathVariable Long groupId) {
        return repo.findByGroupId(groupId);
    }

    // vytvorenie novej úlohy
    @PostMapping
    public void createTask(@RequestBody Task task) {
        repo.save(task);
    }

    // zmena statusu úlohy (TODO / IN_PROGRESS / DONE)
    @PatchMapping("/{taskId}/status")
    public void updateStatus(@PathVariable Long taskId, @RequestBody Task task) {
        repo.updateStatus(taskId, task.getStatus());
    }
}
