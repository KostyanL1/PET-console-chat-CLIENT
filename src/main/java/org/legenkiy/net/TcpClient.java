package org.legenkiy.net;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.service.ApplicationContextServiceImpl;

import java.io.IOException;
import java.net.Socket;


public class TcpClient implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(TcpClient.class);
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();

    @Override
    public void run() {
            try (Socket socket = applicationContextService.getApplicationSocket()) {
                LOGGER.info("TcpClient monitoring started");
                while (true){
                    if (socket != null && !socket.isClosed()) {
                        applicationContextService.getHolder();
                    }
                }
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
