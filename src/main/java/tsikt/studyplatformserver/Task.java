package tsikt.studyplatformserver;

public class Task {

    private Long id;
    private Long groupId;
    private String title;
    private String description;
    private String status;
    private String deadline;
    private String createdAt;
    private String createdBy;

    public Task() {
        // prázdny konštruktor pre RowMapper a Jackson
    }
    public Task(Long id, Long groupId, String title, String description,
                String status, String deadline, String createdAt) {
        this.id = id;
        this.groupId = groupId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
