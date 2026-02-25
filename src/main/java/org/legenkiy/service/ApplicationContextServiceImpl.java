package org.legenkiy.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.state.ApplicationContextHolder;
import org.legenkiy.state.ChatState;
import org.legenkiy.state.ClientState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationContextServiceImpl implements ApplicationContextService {

    private final static Logger LOGGER = LogManager.getLogger(ApplicationContextServiceImpl.class);


    @Override
    public ApplicationContextHolder getHolder() {
        return ApplicationContextHolder.getHolder();
    }

    @Override
    public PrintWriter getApplicationPrintWriter() {
        return getHolder().getPrintWriter();
    }

    @Override
    public Socket getApplicationSocket() {
        return getHolder().getSocket();
    }

    @Override
    public ClientState getClientState() {
        return getHolder().getClientState();
    }

    @Override
    public int getProtocolVer() {
        return getHolder().getPROTOCOL_VER();
    }

    @Override
    public BufferedReader getApplicationBufferedReader() {
        return getHolder().getBufferedReader();
    }

    @Override
    public ChatState getChatState(){
        return getHolder().getChatState();
    }

    @Override
    public void connect(Socket socket) {
        try {
            ApplicationContextHolder holder = getHolder();
            holder.setSocket(socket);
            holder.setPrintWriter(new PrintWriter(socket.getOutputStream(), true));
            holder.setBufferedReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));

            LOGGER.info("Successfully connected to {}", socket.getRemoteSocketAddress());
        } catch (IOException e) {
            LOGGER.error("Connection failed: {}", e.getMessage());
            throw new RuntimeException("Could not establish connection", e);
        }
    }

}
