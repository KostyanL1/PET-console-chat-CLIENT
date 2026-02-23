package org.legenkiy.api;

import org.legenkiy.protocol.message.ClientMessage;

public interface SenderService {

    void send(ClientMessage clientMessage);

}
