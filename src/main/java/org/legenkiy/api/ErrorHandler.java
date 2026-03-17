package org.legenkiy.api;

import org.legenkiy.protocol.message.Envelope;

public interface ErrorHandler {

    void handleError(Envelope envelope);

}

