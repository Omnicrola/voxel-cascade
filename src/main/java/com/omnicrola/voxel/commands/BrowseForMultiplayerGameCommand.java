package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by omnic on 4/2/2016.
 */
public class BrowseForMultiplayerGameCommand extends AbstractWorldCommand {
    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.getNetworkManager().startListeningForServers();
        commandPackage.getUiManager().changeScreen(UiScreen.MULTIPLAYER_BROWSE);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
