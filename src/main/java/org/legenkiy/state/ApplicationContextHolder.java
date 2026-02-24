package org.legenkiy.state;

import lombok.Getter;
import lombok.Setter;
import org.legenkiy.state.enums.State;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationContextHolder {

    private static volatile ApplicationContextHolder instance;

    @Getter private final int PROTOCOL_VER = 1;
    @Getter @Setter private ClientState clientState;
    @Getter @Setter private Socket socket;
    @Getter @Setter private PrintWriter printWriter;
    @Getter @Setter private BufferedReader bufferedReader;

    private ApplicationContextHolder() {}

    public static ApplicationContextHolder getHolder() {
        if (instance == null) {
            synchronized (ApplicationContextHolder.class) {
                if (instance == null) {
                    instance = new ApplicationContextHolder();
                    instance.setClientState(new ClientState(State.UNAUTHENTICATED));
                }
            }
        }
        return instance;
    }
}
