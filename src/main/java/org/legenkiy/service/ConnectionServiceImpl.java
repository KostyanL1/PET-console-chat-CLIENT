package org.legenkiy.service;


import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.ConnectionService;
import org.legenkiy.api.SenderService;
import org.legenkiy.net.Receiver;
import org.legenkiy.net.TcpClient;
import org.legenkiy.protocol.enums.MessageType;
import org.legenkiy.protocol.message.Envelope;
import org.legenkiy.state.enums.State;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    @Value("${application.host}")
    private String host;
    @Value("${application.port}")
    private int port;

    private final static Logger LOGGER = LogManager.getLogger(ConnectionServiceImpl.class);

    private final ApplicationContextService applicationContextService;
    private final SenderService senderService;
    private final TcpClient tcpClient;
    private final Receiver receiver;

    @Override
    public void connect() {
        if (applicationContextService.getApplicationSocket() == null) {
            try {
                //for init context holder
                applicationContextService.connect(new Socket(host, port));
                Thread tcpClientThreat = new Thread(tcpClient);
                Thread resiverThread = new Thread(receiver);
                tcpClientThreat.start();
                resiverThread.start();
                System.out.println(applicationContextService.getApplicationBufferedReader());
                System.out.println(applicationContextService.getApplicationPrintWriter());
                senderService.send(
                        Envelope.builder()
                                .type(MessageType.HELLO)
                                .build()
                );
                LOGGER.info("Connected");
            } catch (Exception e) {
                LOGGER.info("Failed to connect to server, {}", e.getMessage());
            }
        } else {
            System.out.println("Already connected");
        }

    }

    @Override
    public void disconnect() {
        try {
            Socket socket = applicationContextService.getApplicationSocket();
            if (!socket.isClosed()) {
                socket.close();
                applicationContextService.clear();
                LOGGER.info("Disconnected");
            }
        } catch (IOException e) {
            LOGGER.info(e);
        }

    }

    @Override
    public boolean isConnected() {
        return !applicationContextService.getClientState().getState().equals(State.UNAUTHENTICATED);
    }
}
