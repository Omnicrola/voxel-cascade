package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.engine.states.GameOverState;
import com.omnicrola.voxel.engine.states.ShadowState;
import com.omnicrola.voxel.network.messages.LoadLevelCommand;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

import java.util.UUID;

/**
 * Created by Eric on 2/27/2016.
 */
public class StartMultiplayerGameCommand extends AbstractWorldCommand {
    private UUID levelUuid;

    public StartMultiplayerGameCommand(UUID levelUuid) {
        this.levelUuid = levelUuid;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.enableState(ActivePlayState.class);
        commandPackage.enableState(ShadowState.class);
        commandPackage.disableState(GameOverState.class);

        LoadLevelCommand loadLevelCommand = new LoadLevelCommand(LevelGeneratorTool.BASIC_LEVEL_UUID.toString());
        ICommandProcessor commandProcessor = commandPackage.getCommandProcessor();
        commandProcessor.addCommand(loadLevelCommand);
        commandPackage.getUiManager().changeScreen(UiScreen.ACTIVE_PLAY);
    }

}
