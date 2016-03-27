package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Created by Eric on 2/21/2016.
 */
@Serializable
public class HandshakeMessage extends AbstractMessage {

    private int version;

    public HandshakeMessage() {
        super(true);
    }

    public HandshakeMessage(int version) {
        this();
        this.version = version;
    }

    public int getVersion() {
        return this.version;
    }
}
