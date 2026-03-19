package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.legenkiy.api.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {
    private final ConnectionService connectionService;
    private final AuthService authService;
    private final ChatService chatService;

    @Override
    public void handle(String command) {
        switch (command) {
            case "/connect" -> connectionService.connect();
            case "/disconnect" -> connectionService.disconnect();
            case "/register" -> authService.register();
            case "/login" -> authService.login();
            case "/chat" -> chatService.sendChatRequest();
            default -> System.out.println("Unknown command");
        }
    }
}
