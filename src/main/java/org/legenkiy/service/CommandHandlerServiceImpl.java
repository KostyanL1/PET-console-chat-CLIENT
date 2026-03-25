package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.legenkiy.api.*;
import org.legenkiy.state.enums.State;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {
    private final ConnectionService connectionService;
    private final AuthService authService;
    private final ChatService chatService;
    private final ApplicationContextService applicationContextService;

    @Override
    public void handle(String command) {
        if (applicationContextService.getClientState().getState().equals(State.AWAITING_CHAT_CONFIRMATION)){
            switch (command) {
                case "Y" -> chatService.acceptChat();
                case "N" -> chatService.rejectChat();
                default -> System.out.println("> Unknown command. Enter Y to accept, N to reject.");
            }
        }else {
            switch (command) {
                case "/connect" -> connectionService.connect();
                case "/disconnect" -> connectionService.disconnect();
                case "/register" -> authService.register();
                case "/login" -> authService.login();
                case "/chat" -> chatService.sendChatRequest();
                default -> System.out.println("> Unknown command");
            }
        }

    }
}
