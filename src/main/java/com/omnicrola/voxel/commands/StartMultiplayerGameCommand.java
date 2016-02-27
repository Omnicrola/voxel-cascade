package com.omnicrola.voxel.commands;

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
//        StartMultiplayerServerCommand startMultiplayerServerCommand = new StartMultiplayerServerCommand();
//        this.commandProcessor.executeCommand(startMultiplayerServerCommand);
//        JoinMultiplayerCommand joinMultiplayerCommand = new JoinMultiplayerCommand("localhost");
//        this.commandProcessor.executeCommand(joinMultiplayerCommand);
//
//        LoadLevelMessage loadLevelMessage = new LoadLevelMessage(LevelGeneratorTool.BASIC_LEVEL_UUID.toString());
//        this.messageProcessor.sendLocal(loadLevelMessage);

    }
}
