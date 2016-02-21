package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Created by Eric on 2/21/2016.
 */
@Serializable
public class LoadLevelMessage extends AbstractMessage {

    private String levelFilename;

    {
        setReliable(true);
    }

    public LoadLevelMessage() {
    }

    public LoadLevelMessage(String levelFilename) {
        this.levelFilename = levelFilename;
    }
}
