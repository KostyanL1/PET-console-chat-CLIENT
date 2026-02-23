package org.legenkiy.service;

import org.legenkiy.api.ChatService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.mapper.JsonCodec;
import org.legenkiy.protocol.message.ClientMessage;

import java.util.Scanner;


public class ChatServiceImpl implements ChatService {

    private final JsonCodec mapper = new JsonCodec();
    private final SenderService senderService = new SenderServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void startChat() {
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        System.out.println("> Chat with " + username  + " started");
        System.out.println("> Enter message. If you want to end chat write /end");
        String message;
        while (!(message = scanner.nextLine()).equals("/end")){
             senderService.send(
                     ClientMessage.message(username, message)
             );
        }


    }


}
