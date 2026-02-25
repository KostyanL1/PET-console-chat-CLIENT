package org.legenkiy.api;


import org.legenkiy.protocol.message.ServerMessage;

public interface ChatRequestHandlerService {

    void handle(ServerMessage serverMessage);

    boolean isChatting();

    void handleChatRequest();

    void endChat();

}
