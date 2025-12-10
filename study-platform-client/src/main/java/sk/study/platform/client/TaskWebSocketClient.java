package sk.study.platform.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

public class TaskWebSocketClient implements WebSocket.Listener {

    private final Consumer<String> messageConsumer;
    private WebSocket webSocket;

    public TaskWebSocketClient(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void connect(String wsUrl) {
        HttpClient client = HttpClient.newHttpClient();

        client.newWebSocketBuilder()
                .buildAsync(URI.create(wsUrl), this)
                .thenAccept(ws -> this.webSocket = ws)
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });
    }

    public void close() {
        if (webSocket != null) {
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "bye");
        }
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        this.webSocket = webSocket;
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket,
                                     CharSequence data,
                                     boolean last) {
        messageConsumer.accept(data.toString());
        webSocket.request(1);
        return CompletableFuture.completedStage(null);
    }
}
