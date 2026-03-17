package org.legenkiy.handlers;

import org.legenkiy.api.ErrorHandler;
import org.legenkiy.protocol.message.Envelope;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandlerImpl implements ErrorHandler {

    @Override
    public void handleError(Envelope envelope) {
        System.out.println(envelope.getPayload().toString());
    }

}
