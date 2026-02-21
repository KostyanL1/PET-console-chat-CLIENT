package org.legenkiy.state;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.Socket;

public class ApplicationContextHolder {

    private static volatile ApplicationContextHolder applicationContextHolder;
    @Getter
    @Setter
    private ClientState clientState;
    @Getter
    @Setter
    private Socket socket = new Socket("localhost", 1010);


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
