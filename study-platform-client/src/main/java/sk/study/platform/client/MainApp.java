package sk.study.platform.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainApp extends Application {

    private final BackendClient backendClient = new BackendClient();
    private final Gson gson = new Gson();

    private final ListView<Group> groupListView = new ListView<>();
    private final TextArea detailArea = new TextArea();
    private Group selectedGroup;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Study Platform Client");

        Button loadGroupsBtn = new Button("Načítaj skupiny");
        loadGroupsBtn.setOnAction(e -> loadGroups());

        TextField newResName = new TextField();
        newResName.setPromptText("Názov materiálu");

        TextField newResUrl = new TextField();
        newResUrl.setPromptText("URL materiálu");

        Button addResBtn = new Button("Pridaj materiál");
        addResBtn.setOnAction(e -> addResource(newResName.getText(), newResUrl.getText()));


        TextField newTaskTitle = new TextField();
        newTaskTitle.setPromptText("Názov úlohy");

        TextField newTaskDeadline = new TextField();
        newTaskDeadline.setPromptText("Deadline (napr. 2025-12-31)");

        Button addTaskBtn = new Button("Pridaj úlohu");
        addTaskBtn.setOnAction(e -> addTask(newTaskTitle.getText(), newTaskDeadline.getText()));

        HBox topBar = new HBox(
                10,
                loadGroupsBtn,
                newTaskTitle, newTaskDeadline, addTaskBtn,
                newResName, newResUrl, addResBtn
        );
        topBar.setPadding(new Insets(10));


        detailArea.setEditable(false);
        detailArea.setWrapText(true);

        groupListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    selectedGroup = newVal;
                    showGroupDetail(newVal);
                });


        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setLeft(groupListView);
        root.setCenter(detailArea);
        BorderPane.setMargin(groupListView, new Insets(10));
        BorderPane.setMargin(detailArea, new Insets(10));

        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void loadGroups() {
        try {
            String json = backendClient.get("/api/groups");

            Type listType = new TypeToken<List<Group>>() {}.getType();
            List<Group> groups = gson.fromJson(json, listType);

            groupListView.getItems().setAll(groups);
            detailArea.setText("Načítaných skupín: " + groups.size());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            showError("Chyba pri načítaní skupín: " + ex.getClass().getSimpleName()
                    + " - " + ex.getMessage());
        }
    }

    private void showGroupDetail(Group group) {
        if (group == null) {
            detailArea.clear();
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(group.getId()).append("\n");
        sb.append("Názov: ").append(group.getName()).append("\n");
        sb.append("Popis: ").append(group.getDescription()).append("\n");

        // ÚLOHY
        try {
            String jsonTasks = backendClient.get("/api/tasks/by-group/" + group.getId());
            java.lang.reflect.Type taskListType =
                    new com.google.gson.reflect.TypeToken<java.util.List<Task>>() {}.getType();
            java.util.List<Task> tasks = gson.fromJson(jsonTasks, taskListType);

            sb.append("\nÚlohy:\n");
            if (tasks.isEmpty()) {
                sb.append("  - žiadne úlohy\n");
            } else {
                for (Task t : tasks) {
                    sb.append("  - [").append(t.getStatus()).append("] ")
                            .append(t.getTitle());
                    if (t.getDeadline() != null) {
                        sb.append(" (do ").append(t.getDeadline()).append(")");
                    }
                    sb.append("\n");
                }
            }
        } catch (Exception ex) {
            sb.append("\nChyba pri načítaní úloh: ").append(ex.getMessage()).append("\n");
        }

        // MATERIÁLY
        try {
            String jsonRes = backendClient.get("/api/resources/by-group/" + group.getId());
            java.lang.reflect.Type resListType =
                    new com.google.gson.reflect.TypeToken<java.util.List<Resource>>() {}.getType();
            java.util.List<Resource> resources = gson.fromJson(jsonRes, resListType);

            sb.append("\nMateriály:\n");
            if (resources.isEmpty()) {
                sb.append("  - žiadne materiály\n");
            } else {
                for (Resource r : resources) {
                    sb.append("  - ").append(r.toString()).append("\n");
                }
            }
        } catch (Exception ex) {
            sb.append("\nChyba pri načítaní materiálov: ").append(ex.getMessage()).append("\n");
        }

        detailArea.setText(sb.toString());
    }


    private void showError(String msg) {
        detailArea.setText(msg);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void addTask(String title, String deadline) {
        if (selectedGroup == null) {
            showError("Najprv vyber skupinu.");
            return;
        }
        if (title == null || title.isBlank()) {
            showError("Zadaj názov úlohy.");
            return;
        }

        try {
            String json = "{"
                    + "\"groupId\":" + selectedGroup.getId() + ","
                    + "\"title\":\"" + title.replace("\"", "\\\"") + "\","
                    + "\"description\":\"\","
                    + "\"deadline\":\"" + deadline + "\""
                    + "}";

            backendClient.post("/api/tasks", json);

            // po vytvorení úlohy znova načítaj detail skupiny (aj s úlohami)
            showGroupDetail(selectedGroup);

        } catch (IOException | InterruptedException ex) {
            showError("Chyba pri pridávaní úlohy: " + ex.getMessage());
        }
    }

    private void addResource(String name, String url) {
        if (selectedGroup == null) {
            showError("Najprv vyber skupinu.");
            return;
        }
        if (name == null || name.isBlank()) {
            showError("Zadaj názov materiálu.");
            return;
        }
        if (url == null || url.isBlank()) {
            showError("Zadaj URL materiálu.");
            return;
        }

        try {
            // jednoduchý JSON string – typ dáme LINK a uploadedBy necháme null
            String json = "{"
                    + "\"groupId\":" + selectedGroup.getId() + ","
                    + "\"name\":\"" + name.replace("\"", "\\\"") + "\","
                    + "\"type\":\"LINK\","
                    + "\"url\":\"" + url.replace("\"", "\\\"") + "\","
                    + "\"uploadedBy\":null"
                    + "}";

            backendClient.post("/api/resources", json);

            // po pridaní znova načítaj detail skupiny (aby sa zobrazil nový resource)
            showGroupDetail(selectedGroup);

        } catch (Exception ex) {
            showError("Chyba pri pridávaní materiálu: " + ex.getMessage());
        }
    }

}