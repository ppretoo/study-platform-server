package sk.study.platform.client;

public class Membership {

    private Long id;
    private Long userId;
    private Long groupId;
    private String role;
    private String joinedAt;

    // NOVÉ
    private String userName;

    public Membership(Long id, Long userId, Long groupId,
                      String role, String joinedAt, String userName) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.role = role;
        this.joinedAt = joinedAt;
        this.userName = userName;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getGroupId() { return groupId; }
    public String getRole() { return role; }
    public String getJoinedAt() { return joinedAt; }

    public String getUserName() {
        return userName;
    }
}
