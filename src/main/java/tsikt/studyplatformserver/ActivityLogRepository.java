package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityLogRepository {

    private final JdbcTemplate jdbc;

    public ActivityLogRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<ActivityLog> rowMapper = (rs, rowNum) ->
            new ActivityLog(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getLong("group_id"),
                    rs.getString("action"),
                    rs.getString("details"),
                    rs.getString("created_at")
            );

    public void log(Long userId, Long groupId, String action, String details) {
        jdbc.update(
                "INSERT INTO activity_log(user_id, group_id, action, details) VALUES (?, ?, ?, ?)",
                userId,
                groupId,
                action,
                details
        );
    }

    public List<ActivityLog> findAll() {
        return jdbc.query(
                "SELECT id, user_id, group_id, action, details, created_at " +
                        "FROM activity_log ORDER BY created_at DESC",
                rowMapper
        );
    }

    public List<ActivityLog> findByGroupId(Long groupId) {
        return jdbc.query(
                "SELECT id, user_id, group_id, action, details, created_at " +
                        "FROM activity_log WHERE group_id = ? ORDER BY created_at DESC",
                rowMapper,
                groupId
        );
    }

    public List<ActivityLog> findByUserId(Long userId) {
        return jdbc.query(
                "SELECT id, user_id, group_id, action, details, created_at " +
                        "FROM activity_log WHERE user_id = ? ORDER BY created_at DESC",
                rowMapper,
                userId
        );
    }
}
