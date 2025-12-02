package tsikt.studyplatformserver;

public class ActivityLog {

    private Long id;
    private Long userId;
    private Long groupId;
    private String action;
    private String details;
    private String createdAt;

    public ActivityLog() {
    }

    public ActivityLog(Long id, Long userId, Long groupId,
                       String action, String details, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.action = action;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
