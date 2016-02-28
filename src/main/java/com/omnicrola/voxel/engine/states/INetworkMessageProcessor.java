package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.world.IWorldMessage;

/**
 * Created by omnic on 2/28/2016.
 */
public interface INetworkMessageProcessor {

    void addCommand(IWorldMessage worldCommand);
}
