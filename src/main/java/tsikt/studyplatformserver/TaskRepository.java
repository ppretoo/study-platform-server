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
                "SELECT id, group_id, title, description, status, deadline, created_at " +
                        "FROM tasks WHERE group_id = ?",
                taskRowMapper,
                groupId
        );
    }

    public void save(Task task) {
        // ak status neprišiel, nastavíme TODO
        String status = (task.getStatus() == null || task.getStatus().isBlank())
                ? "TODO"
                : task.getStatus();

        jdbc.update(
                "INSERT INTO tasks(group_id, title, description, status, deadline) " +
                        "VALUES (?, ?, ?, ?, ?)",
                task.getGroupId(),
                task.getTitle(),
                task.getDescription(),
                status,
                task.getDeadline()
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
