package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.network.messages.LoadLevelCommand;
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
        commandPackage.getNetworkManager().startMultiplayerServer();
        LoadLevelCommand loadLevelCommand = new LoadLevelCommand(LevelGeneratorTool.BASIC_LEVEL_UUID.toString());
        ICommandProcessor commandProcessor = commandPackage.getCommandProcessor();
        commandProcessor.addCommand(loadLevelCommand);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
