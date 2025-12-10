package tsikt.studyplatformserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TaskNotificationHandler taskNotificationHandler;

    public WebSocketConfig(TaskNotificationHandler taskNotificationHandler) {
        this.taskNotificationHandler = taskNotificationHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(taskNotificationHandler, "/ws/tasks")
                .setAllowedOrigins("*"); // pre jednoduchý lokálny vývoj
    }
}