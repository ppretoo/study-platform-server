package tsikt.studyplatformserver;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email")
            );

    public List<User> findAll() {
        return jdbc.query(
                "SELECT id, name, email FROM users",
                userRowMapper
        );
    }

    public User findById(Long id) {
        return jdbc.queryForObject(
                "SELECT id, name, email FROM users WHERE id = ?",
                userRowMapper,
                id
        );
    }

    public User findByEmail(String email) {
        try {
            return jdbc.queryForObject(
                    "SELECT id, name, email FROM users WHERE email = ?",
                    (rs, rowNum) -> new User(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email")
                    ),
                    email
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateProfile(Long id, String name, String email) {
        jdbc.update(
                "UPDATE users SET name = ?, email = ? WHERE id = ?",
                name,
                email,
                id
        );
    }

    // na registráciu
    public void saveUser(String name, String email, String passwordHash) {
        jdbc.update(
                "INSERT INTO users(name, email, password_hash) VALUES (?, ?, ?)",
                name, email, passwordHash
        );
    }

    // vnútorný objekt s hashom – nepôjde von cez API
    public AuthUser findAuthUserByEmail(String email) {
        try {
            return jdbc.queryForObject(
                    "SELECT id, name, email, password_hash FROM users WHERE email = ?",
                    (rs, rowNum) -> new AuthUser(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password_hash")
                    ),
                    email
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public static class AuthUser {
        private final Long id;
        private final String name;
        private final String email;
        private final String passwordHash;

        public AuthUser(Long id, String name, String email, String passwordHash) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.passwordHash = passwordHash;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPasswordHash() { return passwordHash; }
    }
}