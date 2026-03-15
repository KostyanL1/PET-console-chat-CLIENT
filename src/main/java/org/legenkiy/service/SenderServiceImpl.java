package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.SenderService;
import org.legenkiy.mapper.MessageMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {

    private final Logger LOGGER = LogManager.getLogger(SenderServiceImpl.class);

    private final ApplicationContextService applicationContextService;
    private final MessageMapper mapper;

    @Override
    public void send(ClientMessage clientMessage) {
        try {
            applicationContextService.getApplicationPrintWriter().println(
                    mapper.encode(
                            clientMessage
                    )
            );
            LOGGER.warn("ClientMessage sent to server, {}", clientMessage.getMessageType());
        } catch (Exception e) {
            LOGGER.info("Message failed to send, {}", e.getMessage());
        }
    }
}
