package tsikt.studyplatformserver;

public class Membership {

    private Long id;
    private Long userId;
    private Long groupId;
    private String role;
    private String joinedAt; // zatiaľ ako String (SQLite TEXT)

    public Membership() {
    }

    public Membership(Long id, Long userId, Long groupId, String role, String joinedAt) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getJoinedAt() { return joinedAt; }
    public void setJoinedAt(String joinedAt) { this.joinedAt = joinedAt; }
}
