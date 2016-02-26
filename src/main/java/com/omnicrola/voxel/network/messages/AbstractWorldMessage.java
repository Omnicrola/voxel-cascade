package com.omnicrola.voxel.network.messages;

import com.jme3.network.AbstractMessage;
import com.omnicrola.voxel.world.IWorldMessage;

/**
 * Created by Eric on 2/25/2016.
 */
public abstract class AbstractWorldMessage extends AbstractMessage implements IWorldMessage {
    private long targetTic;

    {
        setReliable(true);
    }

    @Override
    public long getTargetTic() {
        return this.targetTic;
    }

}
