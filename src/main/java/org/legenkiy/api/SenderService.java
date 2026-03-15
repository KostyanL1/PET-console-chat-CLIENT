package org.legenkiy.api;

import org.legenkiy.protocol.message.Envelope;

public interface SenderService {

    void send(Envelope envelope);

}
