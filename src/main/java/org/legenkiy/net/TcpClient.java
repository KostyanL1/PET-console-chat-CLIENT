package org.legenkiy.net;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.state.ApplicationContextHolder;
import org.legenkiy.state.ClientState;
import org.legenkiy.state.enums.State;

import java.io.IOException;
import java.net.Socket;


public class TcpClient implements Runnable{

    private static final Logger LOGGER = LogManager.getLogger(TcpClient.class);

    @Override
    public void run() {
        try (Socket socket = ApplicationContextHolder.getHolder().getSocket()) {
            LOGGER.info("Connected to server on port {}", 1010);
            ClientState clientState = new ClientState(State.UNAUTHENTICATED);
            ApplicationContextHolder.getHolder().setClientState(clientState);
        } catch (Exception e) {
            LOGGER.info(e);
        }
        finally {
            try {
                Socket socket = ApplicationContextHolder.getHolder().getSocket();
                if (!socket.isClosed()){
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
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            LOGGER.info(e);
        }
    }

}
