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

        TextField newTaskTitle = new TextField();
        newTaskTitle.setPromptText("Názov úlohy");

        TextField newTaskDeadline = new TextField();
        newTaskDeadline.setPromptText("Deadline (napr. 2025-12-31)");

        Button addTaskBtn = new Button("Pridaj úlohu");
        addTaskBtn.setOnAction(e -> addTask(newTaskTitle.getText(), newTaskDeadline.getText()));

        HBox topBar = new HBox(10, loadGroupsBtn, newTaskTitle, newTaskDeadline, addTaskBtn);
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

        // načítame úlohy pre danú skupinu
        try {
            String json = backendClient.get("/api/tasks/by-group/" + group.getId());

            java.lang.reflect.Type listType =
                    new com.google.gson.reflect.TypeToken<java.util.List<Task>>() {}.getType();
            java.util.List<Task> tasks = gson.fromJson(json, listType);

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
        } catch (IOException | InterruptedException ex) {
            sb.append("\n\nChyba pri načítaní úloh: ").append(ex.getMessage());
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

}
