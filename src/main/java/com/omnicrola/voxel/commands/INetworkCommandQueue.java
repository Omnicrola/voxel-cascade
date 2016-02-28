package com.omnicrola.voxel.commands;

/**
 * Created by omnic on 2/28/2016.
 */
public interface INetworkCommandQueue {
    void add(IWorldCommand worldCommand);
}
