package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.world.MessagePackage;

/**
 * Created by Eric on 2/24/2016.
 */
public class JoinMultiplayerCommand implements ILocalCommand{
    private String serverAddress;

    public JoinMultiplayerCommand(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    @Override
    public void execute(MessagePackage messagePackage) {
        INetworkManager networkManager  = messagePackage.getNetworkManager();
        networkManager.connectTo(this.serverAddress);
    }
}
