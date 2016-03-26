package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Created by omnic on 3/25/2016.
 */
@Serializable
public class StartGameMessage extends AbstractMessage {
    {
        setReliable(true);
    }
}
