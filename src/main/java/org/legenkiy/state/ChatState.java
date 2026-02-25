package org.legenkiy.state;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ChatState {
    @Getter
    @Setter
    private String chatterUsername;
    @Getter
    @Setter
    private boolean chatting;


    public void initChatState(String chatterUsername){
        this.chatterUsername = chatterUsername;
        this.chatting = true;
    }

    public void clearChatState(){
        this.chatting = false;
        this.chatterUsername = null;
    }



}
