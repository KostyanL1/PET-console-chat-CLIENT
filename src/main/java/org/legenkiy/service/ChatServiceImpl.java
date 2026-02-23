package org.legenkiy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ChatService;
import org.legenkiy.protocol.mapper.JsonCodec;


public class ChatServiceImpl implements ChatService {

    private final JsonCodec mapper = new JsonCodec();
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();

    @Override
    public void connectToChat(String username) {
    }

    @Override
    public void sendMessage(String message) {
    }


}
