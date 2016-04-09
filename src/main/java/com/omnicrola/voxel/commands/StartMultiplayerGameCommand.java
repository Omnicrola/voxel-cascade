package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.engine.states.LoadLevelState;
import com.omnicrola.voxel.world.CommandPackage;

import java.util.UUID;

/**
 * Created by Eric on 2/27/2016.
 */
@Serializable
public class StartMultiplayerGameCommand extends AbstractWorldCommand {

    private String levelUuid;

    public StartMultiplayerGameCommand() {
    }

    public StartMultiplayerGameCommand(UUID levelUuid) {
        this.levelUuid = levelUuid.toString();
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        LoadLevelState loadLevelState = commandPackage.getState(LoadLevelState.class);
        loadLevelState.setLevelToLoad(UUID.fromString(this.levelUuid));
        loadLevelState.setEnabled(true);

//        commandPackage.enableState(ActivePlayState.class);
//        commandPackage.enableState(ShadowState.class);
//        commandPackage.disableState(GameOverState.class);

//        commandPackage.getLevelManager().loadLevel(UUID.fromString(this.levelUuid));
//        commandPackage.getUiManager().changeScreen(UiScreen.ACTIVE_PLAY);
    }

}
