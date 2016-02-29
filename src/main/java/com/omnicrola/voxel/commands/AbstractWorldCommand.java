package com.omnicrola.voxel.commands;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 * Created by omnic on 2/28/2016.
 */
@Serializable
public abstract class AbstractWorldCommand extends AbstractMessage implements IWorldCommand {
    private boolean isLocal;

    {
        setReliable(true);
    }

    @Override
    public boolean isLocal() {
        return this.isLocal;
    }

    @Override
    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }
}
