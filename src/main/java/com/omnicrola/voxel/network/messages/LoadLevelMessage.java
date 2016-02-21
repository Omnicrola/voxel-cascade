package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

import java.util.UUID;

/**
 * Created by Eric on 2/21/2016.
 */
@Serializable
public class LoadLevelMessage extends AbstractMessage {

    private UUID levelId;

    {
        setReliable(true);
    }

    public LoadLevelMessage() {
    }

    public LoadLevelMessage(UUID levelId) {
        this.levelId = levelId;
    }


    public UUID getLevelId() {
        return levelId;
    }
}
