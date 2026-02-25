package org.legenkiy.service;

import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatRequestHandlerService;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.enums.MessageType;
import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.protocol.message.ServerMessage;

import java.util.Scanner;

public class ChatRequestHandlerServiceImpl implements ChatRequestHandlerService {
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();
    private final SenderService senderService = new SenderServiceImpl();
    private final ChatService chatService = new ChatServiceImpl();
    private Scanner scanner = new Scanner(System.in);



    @Override
    public void handle(ServerMessage serverMessage) {
        if (isChatting()){
            ClientMessage clientMessage = new ClientMessage();
            clientMessage.setFrom(applicationContextService.getClientState().getUsername());
            clientMessage.setMessageType(MessageType.ERROR);
            clientMessage.setContent("User is chatting now");
            senderService.send(
                    clientMessage
            );
        }else {
            System.out.println("\u001b[32m" + serverMessage.getFrom() + " wants to chat. Will you accept it? Write Y - yes or N - no." + "\u001b[0m");
            String command = scanner.nextLine();
            switch (command){
                case "Y" -> {
                    applicationContextService.getHolder().getChatState().initChatState(serverMessage.getFrom());
                    chatService.startChat();
                }
                case "N" -> {
                    senderService.send(
                            ClientMessage.chatRejected()
                    );
                    System.out.println("Chat declined");
                }
                default -> {
                    System.out.println("Unknown command");
                }
            }
        }
    }

    @Override
    public void handleChatRequest(){
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        ClientMessage clientMessage = ClientMessage.chatRequest(applicationContextService.getClientState().getUsername() , username);
        senderService.send(
                clientMessage
        );
        System.out.println("> Chat request sent");
    }

    @Override
    public boolean isChatting() {
        return applicationContextService.getChatState().isChatting();
    }

    @Override
    public void endChat() {

    }
}
