package com.omnicrola.voxel.network.messages;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.commands.AbstractWorldCommand;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

import java.util.UUID;

/**
 * Created by Eric on 2/24/2016.
 */
@Serializable
public class LoadLevelMessage extends AbstractWorldCommand {
    private String levelUuid;

    public LoadLevelMessage() {
    }

    public LoadLevelMessage(String levelUuid) {
        this.levelUuid = levelUuid;
    }

    @Override
    public void execute(CommandPackage commandPackage) {

        ILevelManager worldLevelManager = commandPackage.getLevelManager();
        worldLevelManager.loadLevel(UUID.fromString(this.levelUuid));

        IUiManager uiManager = commandPackage.getUiManager();
        uiManager.changeScreen(UiScreen.ACTIVE_PLAY);
    }

}
