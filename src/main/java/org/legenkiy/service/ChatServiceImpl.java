package org.legenkiy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatService;
import org.legenkiy.protocol.mapper.JsonCodec;
import org.legenkiy.protocol.message.ClientMessage;

public class ChatServiceImpl implements ChatService {

    private final JsonCodec mapper = new JsonCodec();
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();

    @Override
    public void connectToChat(String username) throws JsonProcessingException {
        applicationContextService.getApplicationPrintWriter()
                .println(
                        mapper.encode(
                                ClientMessage.connectToPm(
                                        username
                                )
                        )
                );
    }

    @Override
    public void sendMessage(String message) throws JsonProcessingException {
        applicationContextService.getApplicationPrintWriter()
                .println(
                        mapper.encode(
                                ClientMessage.message(message)
                        )
                );
    }



}
