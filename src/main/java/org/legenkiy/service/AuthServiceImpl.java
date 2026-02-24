package org.legenkiy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.AuthService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.dtos.AuthDto;
import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.state.ClientState;
import org.legenkiy.state.enums.State;

import java.util.Scanner;

public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LogManager.getLogger(AuthServiceImpl.class);

    private final Scanner scanner = new Scanner(System.in);
    private final SenderService senderService = new SenderServiceImpl();
    private final ApplicationContextService applicationContextService = new ApplicationContextServiceImpl();

    @Override
    public void register(){
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        System.out.println("> Enter password");
        String password = scanner.nextLine();
        senderService.send(
                ClientMessage.register(
                        new AuthDto(username, password)
                )
        );
        applicationContextService.getHolder().setClientState(new ClientState(username, State.AUTHENTICATED));
    }

    @Override
    public void login() {
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        System.out.println("> Enter password");
        String password = scanner.nextLine();
        senderService.send(
                ClientMessage.login(
                        new AuthDto(username, password)
                )
        );
        applicationContextService.getHolder().setClientState(new ClientState(username, State.AUTHENTICATED));

    }

    @Override
    public void logout() {

    }
}
