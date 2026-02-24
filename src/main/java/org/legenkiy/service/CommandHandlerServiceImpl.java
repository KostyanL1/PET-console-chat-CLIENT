package org.legenkiy.service;

import org.legenkiy.api.AuthService;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.CommandHandlerService;
import org.legenkiy.api.ConnectionService;

public class CommandHandlerServiceImpl implements CommandHandlerService {
    private final ConnectionService connectionService = new ConnectionServiceImpl();
    private final AuthService authService = new AuthServiceImpl();
    private final ChatService chatService = new ChatServiceImpl();

    @Override
    public void handle(String command) {
        switch (command) {
            case "/connect" -> {
                connectionService.connect();
            }
            case "/disconnect" -> {
                connectionService.disconnect();
            }
            case "/register" -> {
                authService.register();
            }
            case "/login" -> {
                if (connectionService.isConnected()){
                    authService.login();
                }else {
                    System.out.println("Connection needed");
                }
            }
            case "/chat" -> {
                if (connectionService.isConnected()){
                    chatService.startChat();
                }else {
                    System.out.println("Connection needed");
                }
            }
            default -> {
                System.out.println("Unknown command");
            }
        }

    }
}
