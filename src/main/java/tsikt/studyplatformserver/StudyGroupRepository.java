package tsikt.studyplatformserver;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudyGroupRepository {

    private final JdbcTemplate jdbc;

    public StudyGroupRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<StudyGroup> groupRowMapper = (rs, rowNum) ->
            new StudyGroup(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description")
            );

    public List<StudyGroup> findAll() {
        return jdbc.query("SELECT id, name, description FROM study_groups", groupRowMapper);
    }

    public void save(StudyGroup group) {
        jdbc.update(
                "INSERT INTO study_groups(name, description) VALUES (?, ?)",
                group.getName(),
                group.getDescription()
        );
    }
}
