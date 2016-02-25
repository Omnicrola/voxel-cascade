package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/24/2016.
 */
public class JoinMultiplayerCommand implements ILocalCommand{
    private String serverAddress;

    public JoinMultiplayerCommand(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        INetworkManager networkManager  = commandPackage.getNetworkManager();
        networkManager.connectTo(this.serverAddress);
    }
}
