package org.legenkiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.ChatService;
import org.legenkiy.api.CommandHandlerService;
import org.legenkiy.config.ApplicationConfig;
import org.legenkiy.state.ApplicationContextHolder;
import org.legenkiy.state.enums.State;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.legenkiy.config.ApplicationConfig.scanner;


public class Application {

    private final static Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.registerShutdownHook();

        LOGGER.info("APPLICATION STARTED");
        ApplicationContextHolder.getHolder();
        CommandHandlerService commandHandlerService = context.getBean(CommandHandlerService.class);
        ChatService chatService = context.getBean(ChatService.class);

        System.out.println("> HELLO :)");
        String input;
        while (!(input = scanner.nextLine()).equals("/exit")) {
            if (ApplicationContextHolder.getHolder().getClientState().getState().equals(State.IN_CHAT)) {
                chatService.sendMessage(input);
            } else {
                commandHandlerService.handle(input);
            }
        }
        System.out.println("> BYE :(");
        LOGGER.info("Application closed");
        System.exit(1);
    }

}
