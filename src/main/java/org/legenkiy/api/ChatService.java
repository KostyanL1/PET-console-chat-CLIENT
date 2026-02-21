package org.legenkiy.api;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ChatService {

    void connectToChat(String username) throws JsonProcessingException;

    void sendMessage(String message) throws JsonProcessingException;

}
