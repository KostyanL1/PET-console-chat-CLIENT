package org.legenkiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.legenkiy.api.CommandHandlerService;
import org.legenkiy.config.ApplicationConfig;
import org.legenkiy.state.ApplicationContextHolder;
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
