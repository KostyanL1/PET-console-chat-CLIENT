package org.legenkiy.net;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.legenkiy.protocol.mapper.JsonCodec;


@AllArgsConstructor
@Getter
@Setter
public class Resiver implements Runnable{

    private final JsonCodec jsonCodec = new JsonCodec();


    @Override
    public void run() {
        while (true){

        }

    }


}
