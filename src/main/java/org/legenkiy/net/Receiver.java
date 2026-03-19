package org.legenkiy.net;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.AuthService;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.ErrorHandler;
import org.legenkiy.mapper.MessageMapper;
import org.legenkiy.protocol.message.Envelope;
import org.springframework.core.env.Environment;
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
    private final ChatService chatService;
    private final AuthService authService;
    private final ErrorHandler errorHandler;
    private final Environment environment;


    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bufferedReader = applicationContextService.getApplicationBufferedReader();
                String message;
                if ((message = bufferedReader.readLine()) != null) {
                    Envelope envelope = mapper.decode(message, Envelope.class);
                    try {
                        switch (envelope.getType()) {
                            case HELLO_ACK -> authService.processResponseHallo();
                            case AUTH_OK -> authService.authenticate(envelope);
                            case CHAT_INCOMING -> chatService.handleIncomingChat(envelope);
                            case CHAT_MSG -> chatService.handleMessage(envelope);
                            case CHAT_END -> chatService.endChat();
                            case ERROR, AUTH_ERROR -> errorHandler.handleError(envelope);
                            default -> {
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error(e);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.info("Failed to read from input stream, {}", e.getMessage());
        }

    }


}
