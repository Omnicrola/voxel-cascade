package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/27/2016.
 */
public class AbortMultiplayerConnectionCommand implements IWorldCommand {
    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.getNetworkManager().shutdownMultiplayer();
        commandPackage.getUiManager().changeScreen(UiScreen.MAIN_MENU);
    }
}
