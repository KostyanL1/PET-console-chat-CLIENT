package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.SenderService;
import org.legenkiy.mapper.MessageMapper;
import org.legenkiy.protocol.message.Envelope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {

    private final Logger LOGGER = LogManager.getLogger(SenderServiceImpl.class);

    private final ApplicationContextService applicationContextService;
    private final MessageMapper mapper;

    @Override
    public void send(Envelope envelope) {
        try {
            applicationContextService.getApplicationPrintWriter().println(
                    mapper.encode(
                            envelope
                    )
            );
            LOGGER.warn("ClientMessage sent to server, {}", envelope.getType());
        } catch (Exception e) {
            LOGGER.info("Message failed to send, {}", e.getMessage());
        }
    }
}
