package org.legenkiy.net;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatRequestHandlerService;
import org.legenkiy.api.ChatService;
import org.legenkiy.protocol.mapper.JsonCodec;

import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.protocol.message.ServerMessage;
import org.legenkiy.service.ApplicationContextServiceImpl;
import org.legenkiy.service.ChatRequestHandlerServiceImpl;
import org.legenkiy.service.ChatServiceImpl;
import org.legenkiy.state.ClientState;

import java.io.BufferedReader;
import java.io.IOException;


@AllArgsConstructor
@Getter
@Setter
public class Resiver implements Runnable {

    private final JsonCodec mapper = new JsonCodec();
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();
    private final Logger LOGGER = LogManager.getLogger(Resiver.class);
    private final ChatRequestHandlerService chatRequestHandlerService = new ChatRequestHandlerServiceImpl();
    private final ChatService chatService = new ChatServiceImpl();


    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bufferedReader = applicationContextService.getApplicationBufferedReader();
                String message;
                if ((message = bufferedReader.readLine()) != null) {
                    ServerMessage serverMessage = mapper.decode(message, ServerMessage.class);
                    switch (serverMessage.getMessageType()) {
                        case OK -> {
                            System.out.println(serverMessage.getContent());
                        }
                        case PM -> {
                            chatRequestHandlerService.handle(serverMessage);
                        }
                        case MSG -> {
                            chatService.handleMessage(serverMessage);
                        }
                        case ACCEPTED -> {
                            chatService.startChat();
                        }
                        case REJECTED -> {
                            System.out.println("Chat request was rejected");
                        }
                        default -> {
                            System.out.println("...");
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.info("Failed to read from input stream, {}", e.getMessage());
        }

    }


}
