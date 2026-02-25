package org.legenkiy.api;

import org.legenkiy.protocol.message.ServerMessage;

public interface ChatService {

    void startChat() ;

    void handleMessage(ServerMessage serverMessage);


}
