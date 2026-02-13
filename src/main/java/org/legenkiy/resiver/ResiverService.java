package org.legenkiy.resiver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.legenkiy.protocol.dtos.AuthDto;
import org.legenkiy.protocol.mapper.JsonCodec;
import org.legenkiy.protocol.message.ServerMessage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class ResiverService implements Runnable{

    private final Socket serverSocket;
    private final JsonCodec jsonCodec = new JsonCodec();


    @Override
    public void run() {
        while (true){
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {
                while (true){
                    String message;
                    if ((message = bufferedReader.readLine()) != null){
                        ServerMessage serverMessage = jsonCodec.decode(message, ServerMessage.class);
                        switch (serverMessage.getMessageType()){
                            case OK -> {
                                AuthDto authDto = jsonCodec.decode(serverMessage.getContent(), AuthDto.class);
                                System.out.println("Authenticate with username : " + authDto.getUsername());
                            }
                        }

                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
