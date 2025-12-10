package tsikt.studyplatformserver;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskNotificationHandler extends TextWebSocketHandler {

    // všetky otvorené WebSocket spojenia
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    /**
     * Zavoláme z TaskController po vytvorení novej úlohy.
     */
    public void broadcastNewTask(Task task) {
        String text = String.format(
                "Nová úloha v skupine %d: %s%s — vytvoril: %s",
                task.getGroupId(),
                task.getTitle(),
                task.getDeadline() != null ? " (do " + task.getDeadline() + ")" : "",
                task.getCreatedBy() != null ? task.getCreatedBy() : "neznámy"
        );

        TextMessage message = new TextMessage(text);

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    // pri chybe session zahodíme
                    try {
                        session.close();
                    } catch (IOException ignored) {}
                }
            }
        }
    }
}
