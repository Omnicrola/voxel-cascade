package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/24/2016.
 */
public class StartMultiplayerServerCommand implements IWorldCommand {

    @Override
    public void execute(CommandPackage commandPackage) {
        INetworkManager networkManager = commandPackage.getNetworkManager();
        networkManager.startMultiplayerServer();
    }
}
