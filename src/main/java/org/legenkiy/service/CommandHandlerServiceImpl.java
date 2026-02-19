package org.legenkiy.service;

import org.legenkiy.api.CommandHandlerService;
import org.legenkiy.api.ConnectionService;

public class CommandHandlerServiceImpl implements CommandHandlerService {
    private final ConnectionService connectionService = new ConnectionServiceImpl();

    @Override
    public void handle(String command) {
        switch (command){
            case "/connect" ->{
                connectionService.connect();
            }
            default -> {
                System.out.println("Unknown command");
            }
        }

    }
}
