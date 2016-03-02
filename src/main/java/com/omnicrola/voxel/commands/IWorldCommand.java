package com.omnicrola.voxel.commands;

import com.jme3.network.Message;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/24/2016.
 */
public interface IWorldCommand extends Message {

    void execute(CommandPackage commandPackage);

    boolean isLocal();

    void setIsLocal(boolean isLocal);
}
