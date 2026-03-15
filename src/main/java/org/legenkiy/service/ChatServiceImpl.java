package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.SenderService;
import org.legenkiy.mapper.MessageMapper;
import org.legenkiy.protocol.dtos.ChatAcceptPayload;
import org.legenkiy.protocol.dtos.ChatIncomingPayload;
import org.legenkiy.protocol.dtos.ChatRejectPayload;
import org.legenkiy.protocol.dtos.ChatRequestPayload;
import org.legenkiy.protocol.enums.MessageType;
import org.legenkiy.protocol.message.Envelope;
import org.legenkiy.state.ClientState;
import org.legenkiy.state.enums.State;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final SenderService senderService;
    private final Scanner scanner = new Scanner(System.in);
    private final ApplicationContextService applicationContextService;

    @Override
    public void handleIncomingChat(Envelope envelope){
        ChatIncomingPayload chatIncomingPayload = (ChatIncomingPayload) envelope.getPayload();

        if (applicationContextService.getClientState().getState().equals(State.AUTHENTICATED)){
            System.out.println("\u001b[32m" + chatIncomingPayload.getFrom() + " wants to chat. Will you accept it? Write Y - yes or N - no." + "\u001b[0m");
            String command = scanner.nextLine();
            switch (command) {
                case "Y" -> {
                    senderService.send(
                            Envelope.builder()
                                    .type(MessageType.CHAT_ACCEPT)
                                    .payload(new ChatAcceptPayload(chatIncomingPayload.getRequestId()))
                                    .build()
                    );
                    applicationContextService.getHolder().getClientState().setState(State.IN_CHAT);
                }
                case "N" -> {
                    senderService.send(
                            Envelope.builder()
                                    .type(MessageType.CHAT_REJECT)
                                    .payload(new ChatRejectPayload(chatIncomingPayload.getRequestId()))
                                    .build()
                    );
                    System.out.println("Chat declined");
                }
                default -> System.out.println("Unknown command");
            }
        } else {
            senderService.send(
                    Envelope.builder()
                            .type(MessageType.CHAT_REJECT)
                            .payload(new ChatRejectPayload(chatIncomingPayload.getRequestId()))
                            .build()
            );
        }
    }

    @Override
    public void startChat() {
        System.out.print("\033[H\033[2J");
        String recipientUsername = applicationContextService.getChatState().getChatterUsername();
        System.out.println("> Chat with " + recipientUsername + " started");
        System.out.println("> Enter message. If you want to end chat write /end");
        boolean flag = true;
        while (flag) {
            String message = scanner.nextLine();
            if (message.equals("/end")) {
                applicationContextService.getChatState().clearChatState();
                flag = false;
            }
            ClientState state = applicationContextService.getHolder().getClientState();
            ClientMessage clientMessage = ClientMessage.message(state.getUsername(), recipientUsername, message);
            senderService.send(
                    clientMessage
            );
        }
        System.out.println("> Chat with " + recipientUsername + " ended");
    }

    @Override
    public void handleMessage(ServerMessage serverMessage) {
        ChatState state = applicationContextService.getChatState();
        if (state.isChatting() && state.getChatterUsername().equals(serverMessage.getFrom())) {
            System.out.println(serverMessage.getFrom() + ": " + serverMessage.getContent());
        }
    }


}
