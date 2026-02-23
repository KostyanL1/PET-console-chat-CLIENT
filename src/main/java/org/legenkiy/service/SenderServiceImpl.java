package org.legenkiy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.mapper.JsonCodec;
import org.legenkiy.protocol.message.ClientMessage;

public class SenderServiceImpl implements SenderService {

    private final Logger LOGGER = LogManager.getLogger(SenderServiceImpl.class);

    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();
    private final JsonCodec mapper = new JsonCodec();

    @Override
    public void send(ClientMessage clientMessage) {
        try {
            applicationContextService.getApplicationPrintWriter().println(
                    mapper.encode(
                            clientMessage
                    )
            );
            LOGGER.info("ClientMessage sent to server, {}", clientMessage.getMessageType());
        } catch (JsonProcessingException e) {
            LOGGER.info("Message failed to send, {}", e.getMessage());
        }
    }
}
