package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by omnic on 4/2/2016.
 */
public class StopBrowsingForMultiplayerGameCommand extends AbstractWorldCommand {
    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.getNetworkManager().stopListeningForServers();
        commandPackage.getUiManager().changeScreen(UiScreen.MAIN_MENU);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
