package com.omnicrola.voxel.world;

import com.jme3.network.Message;
import com.jme3.network.serializing.Serializable;

/**
 * Created by Eric on 2/22/2016.
 */
@Serializable
public interface IWorldMessage extends Message{
    public long getTargetTic();

    void execute(CommandPackage commandPackage);
}
