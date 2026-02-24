package org.legenkiy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.RequestService;
import org.legenkiy.protocol.mapper.JsonCodec;
import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.state.ApplicationContextHolder;

import java.io.IOException;

public class RequestServiceImpl implements RequestService {

    private final JsonCodec mapper = new JsonCodec();
    private final Logger LOGGER = LogManager.getLogger(RequestServiceImpl.class);
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();

    @Override
    public void sendHello() {
        try {
            ApplicationContextHolder
                    .getHolder()
                    .getPrintWriter()
                    .println(
                            mapper.encode(
                                    ClientMessage.hello(
                                            applicationContextService.getProtocolVer()
                                    )
                            )
                    );
            LOGGER.warn("Hello sent to server");
        } catch (IOException e) {
            LOGGER.info("Failed to send hello to server");
            throw new RuntimeException(e);
        }

    }
}
