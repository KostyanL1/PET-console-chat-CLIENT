package org.legenkiy.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ConnectionService;
import org.legenkiy.api.RequestService;
import org.legenkiy.api.SenderService;
import org.legenkiy.net.Resiver;
import org.legenkiy.net.TcpClient;
import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.state.enums.State;

import java.io.IOException;
import java.net.Socket;

public class ConnectionServiceImpl implements ConnectionService {

    private final String HOST = "localhost";
    private final int PORT = 1010;

    private final static Logger LOGGER = LogManager.getLogger(ConnectionServiceImpl.class);

    private final RequestService requestService = new RequestServiceImpl();
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();
    private final SenderService senderService = new SenderServiceImpl();

    @Override
    public void connect() {
        TcpClient tcpClient = new TcpClient();
        Resiver resiver = new Resiver();
        try {
            //for init context holder
            applicationContextService.connect(new Socket(HOST, PORT));
            Thread tcpClientThreat = new Thread(tcpClient);
            Thread resiverThread = new Thread(resiver);
            tcpClientThreat.start();
            resiverThread.start();
            senderService.send(
                    ClientMessage.hello(applicationContextService.getProtocolVer())
            );
            LOGGER.info("Connected");
        } catch (Exception e) {
            LOGGER.info("Failed to connect to server, {}", e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        try {
            Socket socket = applicationContextService.getApplicationSocket();
            if (!socket.isClosed()) {
                socket.close();
                LOGGER.info("Disconnected");
            }
        } catch (IOException e) {
            LOGGER.info(e);
        }

    }

    @Override
    public boolean isConnected() {
        return applicationContextService.getApplicationPrintWriter() != null;
    }
}
