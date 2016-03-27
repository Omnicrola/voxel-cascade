package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 3/27/2016.
 */
public class CreateMultiplayerGameCommand extends AbstractWorldCommand {
    public CreateMultiplayerGameCommand() {
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        INetworkManager networkManager = commandPackage.getNetworkManager();
        networkManager.startLocalMultiplayerServer();
        commandPackage.getUiManager().changeScreen(UiScreen.MULTIPLAYER_CREATE);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
