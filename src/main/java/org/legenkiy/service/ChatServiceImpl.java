package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.dtos.*;
import org.legenkiy.protocol.enums.MessageType;
import org.legenkiy.protocol.message.Envelope;
import org.legenkiy.state.enums.State;
import org.springframework.stereotype.Service;

import static org.legenkiy.config.ApplicationConfig.scanner;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService, Runnable {

    private final SenderService senderService;
    private final ApplicationContextService applicationContextService;

    public void sendChatRequest() {
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        ChatRequestPayload chatRequestPayload = new ChatRequestPayload();
        chatRequestPayload.setTo(username);
        senderService.send(
                Envelope.builder()
                        .type(MessageType.CHAT_REQUEST)
                        .payload(chatRequestPayload)
                        .build()
        );
        System.out.println("> Chat request sent to user: " + username + ". Waiting response.");
    }

    @Override
    public void handleIncomingChat(Envelope envelope) {
        ChatIncomingPayload chatIncomingPayload = (ChatIncomingPayload) envelope.getPayload();

        if (!applicationContextService.getClientState().getState().equals(State.IN_CHAT)) {
            System.out.println("\u001b[32m> " + chatIncomingPayload.getFrom() + " wants to chat. Will you accept it? Write Y - yes or N - no." + "\u001b[0m");
            while (true){
                System.out.println("check");
                String command = scanner.nextLine();
                switch (command) {
                    case "Y" -> {
                        System.out.println("> You accepted chat request");
                        senderService.send(
                                Envelope.builder()
                                        .type(MessageType.CHAT_ACCEPT)
                                        .payload(new ChatAcceptPayload(chatIncomingPayload.getRequestId()))
                                        .build()
                        );
                        applicationContextService.getHolder().getClientState().setState(State.IN_CHAT);
                        applicationContextService.getChatState().setUsername(chatIncomingPayload.getFrom());
                        applicationContextService.getChatState().setId(chatIncomingPayload.getRequestId());
                        Thread thread = new Thread(this);
                        thread.start();
                        return;
                    }
                    case "N" -> {
                        System.out.println("> You rejected chat request");
                        senderService.send(
                                Envelope.builder()
                                        .type(MessageType.CHAT_REJECT)
                                        .payload(new ChatRejectPayload(chatIncomingPayload.getRequestId()))
                                        .build()
                        );
                        System.out.println("> Chat declined");
                        return;
                    }
                    default -> System.out.println("> Unknown command enter Y to accept, N to reject.");
                }
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
        System.out.println("> Chat with " + applicationContextService.getHolder().getChatState().getUsername() + " started");
        System.out.println("> Enter message. If you want to end chat write /end");
        Long chatId = applicationContextService.getChatState().getId();
        String username = applicationContextService.getChatState().getUsername();

        boolean flag = true;
        while (flag) {
            String message = scanner.nextLine();
            if (message.equals("/end")) {
                applicationContextService.getClientState().setState(State.AUTHENTICATED);
                flag = false;
            }
            senderService.send(
                    Envelope.builder()
                            .type(MessageType.CHAT_MSG)
                            .payload(new ChatMessagePayload(chatId, message))
                            .build()
            );
        }
        System.out.println("> Chat with " + username + " ended");
    }

    @Override
    public void endChat() {
        System.out.print("\033[H\033[2J");
        System.out.println("> Chat with " + applicationContextService.getHolder().getChatState().getUsername() + " ended");
        applicationContextService.getChatState().setId(null);
        applicationContextService.getChatState().setUsername(null);
        System.out.print("\033[H\033[2J");
        System.out.println("> Chat with " + applicationContextService.getHolder().getChatState().getUsername() + " ended");
    }

    @Override
    public void handleMessage(Envelope envelope) {
        ChatMessagePayload chatMessagePayload = (ChatMessagePayload) envelope.getPayload();
        if (applicationContextService.getClientState().getState().equals(State.IN_CHAT)) {
            System.out.println("\\u001B[32m" + applicationContextService.getChatState().getUsername() + ": " + chatMessagePayload.getText() + "\\u001B[0m");
        }
    }

    @Override
    public void run() {
        startChat();
    }
}
