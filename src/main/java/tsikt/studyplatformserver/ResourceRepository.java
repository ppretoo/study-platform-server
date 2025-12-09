package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceRepository {

    private final JdbcTemplate jdbc;

    public ResourceRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Resource> resourceRowMapper = (rs, rowNum) -> {
        Resource r = new Resource(
                rs.getLong("id"),
                rs.getLong("group_id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("url"),
                rs.getLong("uploaded_by"),
                rs.getString("uploaded_at")
        );
        r.setCreatedBy(rs.getString("created_by"));
        return r;
    };

    public List<Resource> findByGroupId(Long groupId) {
        return jdbc.query(
                "SELECT id, group_id, name, type, url, uploaded_by, uploaded_at, created_by " +
                        "FROM resources WHERE group_id = ?",
                resourceRowMapper,
                groupId
        );
    }

    public void save(Resource resource) {
        jdbc.update(
                "INSERT INTO resources(group_id, name, type, url, created_by) " +
                        "VALUES (?, ?, ?, ?, ?)",
                resource.getGroupId(),
                resource.getName(),
                resource.getType(),
                resource.getUrl(),
                resource.getCreatedBy()
        );
    }
}
