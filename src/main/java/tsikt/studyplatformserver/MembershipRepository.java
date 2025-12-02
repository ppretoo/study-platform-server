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

    private final RowMapper<Membership> membershipRowMapper = (rs, rowNum) ->
            new Membership(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getLong("group_id"),
                    rs.getString("role"),
                    rs.getString("joined_at")
            );

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
                "SELECT id, user_id, group_id, role, joined_at FROM memberships WHERE group_id = ?",
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
}
