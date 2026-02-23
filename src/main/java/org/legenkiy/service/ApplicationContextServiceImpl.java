package org.legenkiy.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.state.ApplicationContextHolder;
import org.legenkiy.state.ClientState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationContextServiceImpl implements ApplicationContextService {

    private final static Logger LOGGER = LogManager.getLogger(ApplicationContextServiceImpl.class);


    @Override
    public ApplicationContextHolder getHolder() {
        try {
            return ApplicationContextHolder.getHolder();
        } catch (IOException e) {
            LOGGER.info("Context doesn`t exist yet");
            throw new RuntimeException("Failed to get holder. Maybe holder doesn`t exist yet. " + e.getMessage());
        }
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
    public ClientState getState() {
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
}
