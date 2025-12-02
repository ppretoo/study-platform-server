package tsikt.studyplatformserver;

public class Resource {

    private Long id;
    private Long groupId;
    private String name;
    private String type;
    private String url;
    private Long uploadedBy;
    private String uploadedAt;

    public Resource() {
    }

    public Resource(Long id, Long groupId, String name, String type,
                    String url, Long uploadedBy, String uploadedAt) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.type = type;
        this.url = url;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Long getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(Long uploadedBy) { this.uploadedBy = uploadedBy; }

    public String getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(String uploadedAt) { this.uploadedAt = uploadedAt; }
}
