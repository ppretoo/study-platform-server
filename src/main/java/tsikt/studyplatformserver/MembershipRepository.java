package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MembershipRepository {

    private final JdbcTemplate jdbc;

    public MembershipRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Membership> membershipRowMapper = (rs, rowNum) -> {
        Membership m = new Membership(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("group_id"),
                rs.getString("role"),
                rs.getString("joined_at")
        );
        m.setUserName(rs.getString("user_name"));
        return m;
    };

    public void save(Membership m) {
        jdbc.update(
                "INSERT INTO memberships(user_id, group_id, role) VALUES (?, ?, ?)",
                m.getUserId(),
                m.getGroupId(),
                m.getRole()
        );
    }

    public List<Membership> findByGroupId(Long groupId) {
        return jdbc.query(
                "SELECT m.id, m.user_id, m.group_id, m.role, m.joined_at, " +
                        "u.name AS user_name " +
                        "FROM memberships m " +
                        "JOIN users u ON m.user_id = u.id " +
                        "WHERE m.group_id = ?",
                membershipRowMapper,
                groupId
        );
    }

    public List<Membership> findByUserId(Long userId) {
        return jdbc.query(
                "SELECT id, user_id, group_id, role, joined_at FROM memberships WHERE user_id = ?",
                membershipRowMapper,
                userId
        );
    }

    public void insert(Long userId, Long groupId, String role) {
        if (existsByUserAndGroup(userId, groupId)) {
            return;
        }

        jdbc.update(
                "INSERT INTO memberships(user_id, group_id, role) VALUES (?, ?, ?)",
                userId, groupId, role
        );
    }


    public boolean existsByUserAndGroup(Long userId, Long groupId) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM memberships WHERE user_id = ? AND group_id = ?",
                Integer.class,
                userId,
                groupId
        );
        return count != null && count > 0;
    }
}
