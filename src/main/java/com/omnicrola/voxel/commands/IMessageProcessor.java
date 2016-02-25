package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.world.IWorldMessage;

/**
 * Created by Eric on 2/24/2016.
 */
public interface IMessageProcessor {
    void sendLocal(IWorldMessage message);
}
