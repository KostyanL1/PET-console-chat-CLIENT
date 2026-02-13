package org.legenkiy;

import org.legenkiy.protocol.message.ClientMessage;
import org.legenkiy.protocol.mapper.JsonCodec;
import org.legenkiy.resiver.ResiverService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Сlient2 {
    public static void main(String[] args) {


        JsonCodec jsonCodec = new JsonCodec();


        try (Socket socket = new Socket("localhost", 1010)) {
            ResiverService resiverService = new ResiverService(socket);
            Thread thread = new Thread(resiverService);
            thread.start();
            try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String command;
                    command = scanner.nextLine();
                    switch (command) {
                        case "/login" -> {
                            System.out.println("ENTER USERNAME");
                            String username = scanner.nextLine();
                            System.out.println("ENTER PASSWORD");
                            String password = scanner.nextLine();
                            ClientMessage clientMessage = ClientMessage.login(username, password);
                            printWriter.println(jsonCodec.encode(clientMessage));
                            break;
                        }
                        case "/message" -> {
                            System.out.println("ENTER USERNAME FOR SEND MESSAGE");
                            String username = scanner.nextLine();
                            System.out.println("ENTER MESSAGE");
                            String content = scanner.nextLine();
                            ClientMessage clientMessage = ClientMessage.privateMessage(username, content);
                            printWriter.println(jsonCodec.encode(clientMessage));
                            break;
                        }

                        default -> {
                            System.out.println("UNKNOWN COMMAND");
                            break;
                        }

                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
