package org.legenkiy.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ApplicationContextService;
import org.legenkiy.api.AuthService;
import org.legenkiy.api.SenderService;
import org.legenkiy.protocol.dtos.AuthPayload;
import org.legenkiy.protocol.enums.MessageType;
import org.legenkiy.protocol.message.Envelope;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static org.legenkiy.config.ApplicationConfig.scanner;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LogManager.getLogger(AuthServiceImpl.class);


    private final SenderService senderService;
    private final ApplicationContextService applicationContextService;


    @Override
    public void register() {
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        System.out.println("> Enter password");
        String password = scanner.nextLine();
        senderService.send(
                Envelope.builder()
                        .type(MessageType.AUTH_REGISTER)
                        .payload(new AuthPayload(username, password))
                        .build()
        );
    }

    @Override
    public void login() {
        System.out.println("> Enter username");
        String username = scanner.nextLine();
        System.out.println("> Enter password");
        String password = scanner.nextLine();
        senderService.send(
                Envelope.builder()
                        .type(MessageType.AUTH_LOGIN)
                        .payload(new AuthPayload(username, password))
                        .build()
        );
    }

    @Override
    public void logout() {

    }

    @Override
    public void hello() {
        System.out.println("Hello sent");
    }

    @Override
    public void processResponseHallo() {
        System.out.println("Hello was received");
    }

    @Override
    public void authenticate(Envelope envelope) {
        AuthPayload authPayload = (AuthPayload) envelope.getPayload();
        applicationContextService.getClientState().setUsername(authPayload.getUsername());
        System.out.println("Authenticated");
    }
}
