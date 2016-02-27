package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.MessagePackage;

/**
 * Created by Eric on 2/27/2016.
 */
public class AbortMultiplayerConnectionCommand implements ILocalCommand {
    @Override
    public void execute(MessagePackage messagePackage) {
        messagePackage.getNetworkManager().shutdownMultiplayer();
        messagePackage.getUiManager().changeScreen(UiScreen.MAIN_MENU);
    }
}
