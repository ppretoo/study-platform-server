package sk.study.platform.client;

public class Task {
    private Long id;
    private Long groupId;
    private String title;
    private String description;
    private String status;
    private String deadline;

    public Long getId() { return id; }
    public Long getGroupId() { return groupId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getDeadline() { return deadline; }

    @Override
    public String toString() {
        String d = (deadline == null) ? "" : " (do " + deadline + ")";
        return "- [" + status + "] (id=" + id + ") " + title + d;
    }
}