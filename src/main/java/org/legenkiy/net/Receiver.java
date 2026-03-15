package org.legenkiy.net;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatRequestHandlerService;
import org.legenkiy.api.ChatService;
import org.legenkiy.mapper.MessageMapper;
import org.legenkiy.protocol.message.Envelope;
import org.legenkiy.protocol.message.ServerMessage;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;


@Getter
@Setter
@Component
@RequiredArgsConstructor
public class Receiver implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Receiver.class);

    private final MessageMapper mapper;
    private final ApplicationContextService applicationContextService;
    private final ChatRequestHandlerService chatRequestHandlerService;
    private final ChatService chatService;


    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bufferedReader = applicationContextService.getApplicationBufferedReader();
                String message;
                if ((message = bufferedReader.readLine()) != null) {
                    Envelope envelope = mapper.decode(message, Envelope.class);
                    switch (envelope.getType()) {
                        case HELLO_ACK -> System.out.println("HELLO was received");
                        case AUTH_OK -> System.out.println("Authenticated");
                        case CHAT_INCOMING -> chatService.handleIncomingChat(envelope);
                        case CHAT_MSG -> chatService.handleMessage(envelope);
                        case REJECTED -> {
                            System.out.println("Chat request was rejected");
                        }
                        default -> {
                            System.out.println("default");
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.info("Failed to read from input stream, {}", e.getMessage());
        }

    }


}
