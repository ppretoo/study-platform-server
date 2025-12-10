package tsikt.studyplatformserver;

import java.util.List;
import tsikt.studyplatformserver.ActivityLogRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskRepository repo;
    private final ActivityLogRepository activityRepo;
    private final TaskNotificationHandler notificationHandler;

    public TaskController(TaskRepository repo,
                          ActivityLogRepository activityRepo,
                          TaskNotificationHandler notificationHandler) {
        this.repo = repo;
        this.activityRepo = activityRepo;
        this.notificationHandler = notificationHandler;
    }

    @GetMapping("/by-group/{groupId}")
    public List<Task> getTasksByGroup(@PathVariable Long groupId) {
        return repo.findByGroupId(groupId);
    }

    @PostMapping
    public void createTask(@RequestBody Task task) {
        repo.save(task);
        activityRepo.log(null, task.getGroupId(), "TASK_CREATED", "Task: " + task.getTitle());
        notificationHandler.broadcastNewTask(task);
    }

    @PatchMapping("/{taskId}/status")
    public void updateStatus(@PathVariable Long taskId, @RequestBody Task task) {
        repo.updateStatus(taskId, task.getStatus());
        activityRepo.log(null, null, "TASK_STATUS_CHANGED",
                "TaskId: " + taskId + ", status: " + task.getStatus());
    }
}