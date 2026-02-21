package org.legenkiy.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ConnectionService;
import org.legenkiy.net.Resiver;
import org.legenkiy.net.TcpClient;
import org.legenkiy.state.ApplicationContextHolder;

public class ConnectionServiceImpl implements ConnectionService {

    private final static Logger LOGGER = LogManager.getLogger(ConnectionServiceImpl.class);

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
            LOGGER.info("Connected");
        } catch (Exception e) {
            LOGGER.info(e);
        }
    }

    @Override
    public void dissconect() {

    }
}
