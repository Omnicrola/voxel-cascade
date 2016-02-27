package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.network.messages.LoadLevelMessage;
import com.omnicrola.voxel.world.MessagePackage;

import java.util.UUID;

/**
 * Created by Eric on 2/27/2016.
 */
public class StartMultiplayerGameCommand implements ILocalCommand {
    private UUID levelUuid;

    public StartMultiplayerGameCommand(UUID levelUuid) {
        this.levelUuid = levelUuid;
    }

    @Override
    public void execute(MessagePackage messagePackage) {
        messagePackage.getNetworkManager().startMultiplayerServer();
        LoadLevelMessage loadLevelMessage = new LoadLevelMessage(LevelGeneratorTool.BASIC_LEVEL_UUID.toString());
        messagePackage.getMessageProcessor().sendLocal(loadLevelMessage);
    }
}
