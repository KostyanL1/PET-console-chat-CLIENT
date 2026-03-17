package org.legenkiy.api;

import org.legenkiy.protocol.message.Envelope;

public interface AuthService {

    void register();

    void login();

    void logout();

    void hello();

    void processResponseHallo();

    void authenticate(Envelope envelope);

}
