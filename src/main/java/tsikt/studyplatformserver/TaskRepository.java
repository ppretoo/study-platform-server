package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

    private final JdbcTemplate jdbc;

    public TaskRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Task> taskRowMapper = (rs, rowNum) ->
            new Task(
                    rs.getLong("id"),
                    rs.getLong("group_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("status"),
                    rs.getString("deadline"),
                    rs.getString("created_at")
            );

    public List<Task> findByGroupId(Long groupId) {
        return jdbc.query(
                "SELECT id, group_id, title, description, status, deadline, created_by " +
                        "FROM tasks WHERE group_id = ?",
                (rs, rowNum) -> {
                    Task task = new Task();
                    task.setId(rs.getLong("id"));
                    task.setGroupId(rs.getLong("group_id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setStatus(rs.getString("status"));
                    task.setDeadline(rs.getString("deadline"));
                    task.setCreatedBy(rs.getString("created_by")); // ← TENTO RIADOK
                    return task;
                },
                groupId
        );
    }

    public void save(Task task) {
        String status = task.getStatus();
        if (status == null || status.isBlank()) {
            status = "OPEN";
        }

        jdbc.update(
                "INSERT INTO tasks(group_id, title, description, status, deadline, created_by) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                task.getGroupId(),
                task.getTitle(),
                task.getDescription(),
                status,
                task.getDeadline(),
                task.getCreatedBy()
        );
    }

    public void updateStatus(Long taskId, String status) {
        jdbc.update(
                "UPDATE tasks SET status = ? WHERE id = ?",
                status,
                taskId
        );
    }
}
