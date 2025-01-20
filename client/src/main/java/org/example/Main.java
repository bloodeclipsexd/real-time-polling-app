package org.example;

import jakarta.websocket.*;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

@ClientEndpoint
public class Main {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to the server");
        try {
            // Отправляем сообщение серверу
            session.getBasicRemote().sendText("Hello, WebSocket Server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Closed connection: " + closeReason.getReasonPhrase());
    }

    public static void main(String[] args) {
        try {
            ClientManager clientManager = ClientManager.createClient();
            clientManager.getProperties().put(ClientProperties.REDIRECT_ENABLED, true);

            clientManager.connectToServer(Main.class, java.net.URI.create("ws://localhost:8080/ws"));

            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}