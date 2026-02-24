package org.legenkiy.state;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.legenkiy.state.enums.State;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientState {

    private volatile String username;
    private volatile State state;

    public ClientState(State state) {
        this.state = state;
    }

}
