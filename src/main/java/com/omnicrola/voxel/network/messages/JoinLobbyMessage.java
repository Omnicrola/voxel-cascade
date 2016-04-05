package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

import java.util.UUID;

/**
 * Created by omnic on 3/25/2016.
 */
@Serializable
public class JoinLobbyMessage extends AbstractMessage {

    private String lobbyKey;

    public JoinLobbyMessage() {
    }

    public JoinLobbyMessage(UUID lobbyKey) {
        this.lobbyKey = lobbyKey.toString();
    }

    public UUID getLobbyKey() {
        if (this.lobbyKey == null) {
            return null;
        }
        return UUID.fromString(this.lobbyKey);
    }
}
