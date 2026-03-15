package org.legenkiy.net;


import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
@RequiredArgsConstructor
public class TcpClient implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(TcpClient.class);
    private final ApplicationContextService applicationContextService;

    @Override
    public void run() {

        try (Socket socket = applicationContextService.getApplicationSocket()) {

            LOGGER.info("TcpClient monitoring started");
            if (socket != null && !socket.isClosed()) applicationContextService.getHolder();

        } catch (Exception e) {
            LOGGER.info(e);
        } finally {
            try {

                Socket socket = applicationContextService.getApplicationSocket();
                if (!socket.isClosed()) {
                    socket.close();
                    LOGGER.info("Socked closed");
                }

            } catch (IOException e) {
                LOGGER.info("Socket is closed {}", e.getMessage());
            }
        }

    }

}
