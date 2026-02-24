package org.legenkiy.net;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.protocol.mapper.JsonCodec;

import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.protocol.message.ServerMessage;
import org.legenkiy.service.ApplicationContextServiceImpl;
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


    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bufferedReader = applicationContextService.getApplicationBufferedReader();
                String message;
                if ((message = bufferedReader.readLine()) != null) {
                    ServerMessage serverMessage = mapper.decode(message, ServerMessage.class);
                    switch (serverMessage.getMessageType()){
                        case OK -> {
                            System.out.println(serverMessage.getContent());
                        }
                        case MSG -> {
                            System.out.println(serverMessage.getFrom() + ": " + serverMessage.getContent());
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
