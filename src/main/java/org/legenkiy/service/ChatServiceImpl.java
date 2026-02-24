package org.legenkiy.service;

import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.ConnectionService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.mapper.JsonCodec;
import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.state.ClientState;

import java.util.Scanner;


public class ChatServiceImpl implements ChatService {

    private final JsonCodec mapper = new JsonCodec();
    private final SenderService senderService = new SenderServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();

    @Override
    public void startChat() {
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.println("> Chat with " + username + " started");
        System.out.println("> Enter message. If you want to end chat write /end");
        boolean flag = true;
        while (flag) {
            String message = scanner.nextLine();
            if (message.equals("/end")) {
                flag = false;
            }
            ClientState state = applicationContextService.getHolder().getClientState();
            ClientMessage clientMessage = ClientMessage.message(username, message);
            clientMessage.setFrom(state.getUsername());
            senderService.send(
                    clientMessage
            );
        }
        System.out.println("> Chat with " + username + " ended");
    }
}
