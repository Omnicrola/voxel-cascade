package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.network.messages.ChatMessage;

/**
 * Created by omnic on 4/13/2016.
 */
public class ChatMessageEvent {
    private ChatMessage message;

    public ChatMessageEvent(ChatMessage message) {
        this.message = message;
    }

    public ChatMessage getMessage() {
        return message;
    }
}
