package com.omnicrola.voxel.ui.nifty.impl;

import com.omnicrola.voxel.network.messages.ChatMessage;
import com.omnicrola.voxel.ui.nifty.IUiChatbox;
import de.lessvoid.nifty.controls.chatcontrol.ChatControl;

import java.text.MessageFormat;

/**
 * Created by omnic on 4/13/2016.
 */
public class ChatControlWrapper implements IUiChatbox {
    private ChatControl chatControl;

    public ChatControlWrapper(ChatControl chatControl) {
        this.chatControl = chatControl;
    }

    @Override
    public void addMessage(ChatMessage chatMessage) {
        String message = chatMessage.getMessage();
        String timestamp = chatMessage.getTimestamp();
        String sender = chatMessage.getSender();

        String format = "[{0}] {1} : {2}";
        String chatLine = MessageFormat.format(format, sender, timestamp, message);
        chatControl.receivedChatLine(chatLine, null);
    }
}
