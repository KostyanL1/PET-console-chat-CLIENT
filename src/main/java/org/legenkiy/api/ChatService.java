package org.legenkiy.api;

import org.legenkiy.protocol.dtos.ChatIncomingPayload;
import org.legenkiy.protocol.message.Envelope;

public interface ChatService {

    void sendChatRequest();

    void handleIncomingChat(Envelope envelope);

    void startChat(Envelope envelope);

    void endChat();

    void handleMessage(Envelope envelope);

    void sendMessage(String message);

    void acceptChat();

    void rejectChat();


}
