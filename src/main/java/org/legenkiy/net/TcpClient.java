package org.legenkiy.net;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.service.ApplicationContextServiceImpl;
import org.legenkiy.state.ClientState;
import org.legenkiy.state.enums.State;

import java.io.IOException;
import java.net.Socket;


public class TcpClient implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(TcpClient.class);
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();

    @Override
    public void run() {
        try (Socket socket = applicationContextService.getApplicationSocket()) {
            LOGGER.info("Connected to server on port {}", 1010);
            ClientState clientState = new ClientState(State.UNAUTHENTICATED);
            applicationContextService.getHolder().setClientState(clientState);
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

    void closeResources(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

}
