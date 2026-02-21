package org.legenkiy.state;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplicationContextHolder {

    private static volatile ApplicationContextHolder applicationContextHolder;

    @Getter
    private final int PROTOCOL_VER = 1;
    @Getter
    @Setter
    private ClientState clientState;
    @Getter
    @Setter
    private Socket socket;
    @Getter
    private PrintWriter printWriter;

    {
        this.socket = new Socket("localhost", 1010);
        this.printWriter = new PrintWriter(socket.getOutputStream());
    }

    private ApplicationContextHolder() throws IOException {
    }

    public static ApplicationContextHolder getHolder() throws IOException {
        if (applicationContextHolder == null) {
            synchronized (ApplicationContextHolder.class) {
                if (applicationContextHolder == null) {
                    applicationContextHolder = new ApplicationContextHolder();
                }
            }
        }
        return applicationContextHolder;
    }

}
