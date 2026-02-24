package org.legenkiy.api;

public interface ConnectionService {

    void connect();

    void disconnect();

    boolean isConnected();

}
