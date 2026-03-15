package org.legenkiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.state.ApplicationContextHolder;

import java.util.Scanner;

public class Application {

    private final static Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("APPLICATION STARTED");
        ApplicationContextHolder.getHolder();
        System.out.println("> HELLO :)");
        String command;
        while (!(command = scanner.nextLine().replace(" ", "")).equals("/exit")) {
            commandHandlerService.handle(command);
        }
        System.out.println("> BYE :(");
        LOGGER.info("Application closed");
        System.exit(1);
    }

}
