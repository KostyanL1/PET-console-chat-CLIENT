package org.legenkiy.api;


import org.legenkiy.protocol.message.Envelope;

public interface ChatService {

    void handleIncomingChat(Envelope envelope);

    void startChat();

    void handleMessage(ServerMessage serverMessage);


}
