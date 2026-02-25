package org.legenkiy.service;

import org.legenkiy.api.*;

public class CommandHandlerServiceImpl implements CommandHandlerService {
    private final ConnectionService connectionService = new ConnectionServiceImpl();
    private final AuthService authService = new AuthServiceImpl();
    private final ChatRequestHandlerService chatRequestHandlerService = new ChatRequestHandlerServiceImpl();

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
                if (connectionService.isConnected()) {
                    authService.login();
                } else {
                    System.out.println("Connection needed");
                }
            }
            case "/chat" -> {
                if (connectionService.isConnected()) {
                    chatRequestHandlerService.handleChatRequest();
                } else {
                    System.out.println("Connection needed");
                }
            }
            default -> {
                System.out.println("Unknown command");
            }
        }

    }
}
