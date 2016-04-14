package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Created by omnic on 4/13/2016.
 */
@Serializable
public class ChatMessage extends AbstractMessage {
    {
        setReliable(true);
    }

    private String message;
    private String sender;
    private String timestamp;

    public ChatMessage() {
    }

    public ChatMessage(String timestamp, String sender, String message) {
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
