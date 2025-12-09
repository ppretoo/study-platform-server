package sk.study.platform.client;

public class Resource {

    private Long id;
    private Long groupId;
    private String name;
    private String type;
    private String url;
    private Long uploadedBy;

    public Long getId() { return id; }
    public Long getGroupId() { return groupId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getUrl() { return url; }
    public Long getUploadedBy() { return uploadedBy; }

    @Override
    public String toString() {
        String t = (type == null) ? "" : "[" + type + "] ";
        return t + name + " -> " + url;
    }
}
