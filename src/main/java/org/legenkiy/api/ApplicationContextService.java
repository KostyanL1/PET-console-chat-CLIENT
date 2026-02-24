package org.legenkiy.api;

import org.legenkiy.state.ApplicationContextHolder;
import org.legenkiy.state.ClientState;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public interface ApplicationContextService {

    ApplicationContextHolder getHolder();

    PrintWriter getApplicationPrintWriter();

    Socket getApplicationSocket();

    ClientState getClientState();

    int getProtocolVer();

    BufferedReader getApplicationBufferedReader();

    void connect(Socket socket);

}
