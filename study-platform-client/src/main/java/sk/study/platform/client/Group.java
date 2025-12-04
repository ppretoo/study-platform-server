package sk.study.platform.client;

public class Group {
    private Long id;
    private String name;
    private String description;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        // čo sa zobrazí v ListView
        return name + " (id=" + id + ")";
    }
}
