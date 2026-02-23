package org.legenkiy.service;

import org.legenkiy.api.ChatService;
import org.legenkiy.api.CommandHandlerService;
import org.legenkiy.api.ConnectionService;

public class CommandHandlerServiceImpl implements CommandHandlerService {
    private final ConnectionService connectionService = new ConnectionServiceImpl();
    private final ChatService chatService = new ChatServiceImpl();

    @Override
    public void handle(String command) {
        switch (command) {
            case "/connect" -> {
                connectionService.connect();
            }
            case "/disconnect" -> {
                connectionService.dissconect();
            }
            case "/login" -> {

            }
            case "/logout" ->{

            }
            case "/chat" -> {

            }
            default -> {
                System.out.println("Unknown command");
            }
        }

    }
}
