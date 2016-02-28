package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/27/2016.
 */
public class AbortMultiplayerConnectionCommand extends AbstractWorldCommand {
    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.getNetworkManager().shutdownMultiplayer();
        commandPackage.getUiManager().changeScreen(UiScreen.MAIN_MENU);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
