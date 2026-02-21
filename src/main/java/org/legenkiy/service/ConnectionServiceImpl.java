package org.legenkiy.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ConnectionService;
import org.legenkiy.api.RequestService;
import org.legenkiy.net.Resiver;
import org.legenkiy.net.TcpClient;
import org.legenkiy.state.ApplicationContextHolder;

import java.io.IOException;
import java.net.Socket;

public class ConnectionServiceImpl implements ConnectionService {

    private final static Logger LOGGER = LogManager.getLogger(ConnectionServiceImpl.class);

    private final RequestService requestService = new RequestServiceImpl();

    @Override
    public void connect() {
        TcpClient tcpClient = new TcpClient();
        Resiver resiver = new Resiver();
        try {
            ApplicationContextHolder.getHolder();
            Thread tcpClientThreat = new Thread(tcpClient);
            Thread resiverThread = new Thread(resiver);
            tcpClientThreat.start();
            resiverThread.start();
            requestService.sendHello();
            LOGGER.info("Connected");
        } catch (Exception e) {
            LOGGER.info("Failed to connect to server, {}", e.getMessage());
        }
    }

    @Override
    public void dissconect() {
        try {
            Socket socket = ApplicationContextHolder.getHolder().getSocket();
            if (!socket.isClosed()) {
                socket.close();
                LOGGER.info("Disconnected");
            }
        } catch (IOException e) {
            LOGGER.info(e);
        }

    }
}
