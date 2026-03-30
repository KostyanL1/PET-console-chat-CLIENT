package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.dtos.*;
import org.legenkiy.protocol.enums.MessageType;
import org.legenkiy.protocol.message.Envelope;
import org.legenkiy.state.ChatState;
import org.legenkiy.state.enums.State;
import org.springframework.stereotype.Service;


import static org.legenkiy.config.ApplicationConfig.scanner;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final SenderService senderService;
    private final ApplicationContextService applicationContextService;

    public void sendChatRequest() {
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        ChatRequestPayload chatRequestPayload = new ChatRequestPayload();
        chatRequestPayload.setTo(username);
        applicationContextService.getChatState().setUsername(username);
        senderService.send(
                Envelope.builder()
                        .type(MessageType.CHAT_REQUEST)
                        .payload(chatRequestPayload)
                        .build()
        );
        System.out.println("Sent request : " + applicationContextService.getChatState().getId());
    }

    @Override
    public void handleIncomingChat(Envelope envelope) {
        ChatIncomingPayload chatIncomingPayload = (ChatIncomingPayload) envelope.getPayload();
        System.out.println(chatIncomingPayload.getRequestId());
        if (!applicationContextService.getClientState().getState().equals(State.IN_CHAT)) {
            applicationContextService.getClientState().setState(State.AWAITING_CHAT_CONFIRMATION);
            applicationContextService.getChatState().setId(chatIncomingPayload.getRequestId());
            applicationContextService.getChatState().setUsername(chatIncomingPayload.getFrom());
            System.out.println("\u001b[32m> " + chatIncomingPayload.getFrom() + " wants to chat. Will you accept it? Write Y - yes or N - no." + "\u001b[0m");
        } else {
            rejectChat();
        }
    }

    @Override
    public void sendMessage(String message) {
        Long chatId = applicationContextService.getChatState().getId();
        if (!message.equals("/end")) {
            senderService.send(
                    Envelope.builder()
                            .type(MessageType.CHAT_MSG)
                            .payload(new ChatMessagePayload(chatId, message))
                            .build()
            );
        } else {
            endChat();
        }

    }

    @Override
    public void startChat(Envelope envelope) {
        ChatStartedPayload chatStartedPayload = (ChatStartedPayload) envelope.getPayload();
        System.out.println(chatStartedPayload.getChatId());
        applicationContextService.getClientState().setState(State.IN_CHAT);
        applicationContextService.getChatState().setId(chatStartedPayload.getChatId());
        System.out.print("\033[H\033[2J");
        System.out.println("> Chat with " + applicationContextService.getHolder().getChatState().getUsername() + " started");
        System.out.println("> Enter message. If you want to end chat write /end");
    }

    @Override
    public void endChat() {
        System.out.print("\033[H\033[2J");
        System.out.println("> Chat with " + applicationContextService.getHolder().getChatState().getUsername() + " ended");
        applicationContextService.getClientState().setState(State.AUTHENTICATED);
        applicationContextService.getChatState().setId(null);
        applicationContextService.getChatState().setUsername(null);
    }

    @Override
    public void handleMessage(Envelope envelope) {
        ChatMessagePayload chatMessagePayload = (ChatMessagePayload) envelope.getPayload();
        if (applicationContextService.getClientState().getState().equals(State.IN_CHAT)) {
            System.out.println(applicationContextService.getChatState().getUsername() + ": " + chatMessagePayload.getText());
        }
    }

    @Override
    public void acceptChat() {
        System.out.println("> You accepted chat request");
        senderService.send(
                Envelope.builder()
                        .type(MessageType.CHAT_ACCEPT)
                        .payload(new ChatAcceptPayload(applicationContextService.getChatState().getId()))
                        .build()
        );
    }

    @Override
    public void rejectChat() {
        System.out.println("> You rejected chat request");
        senderService.send(
                Envelope.builder()
                        .type(MessageType.CHAT_REJECT)
                        .payload(new ChatRejectPayload(applicationContextService.getChatState().getId()))
                        .build()
        );
        applicationContextService.getClientState().setState(State.AUTHENTICATED);
        System.out.println("> Chat declined");
    }
}
