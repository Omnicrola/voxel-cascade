package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.data.level.LevelSettings;
import com.omnicrola.voxel.engine.states.LoadLevelState;
import com.omnicrola.voxel.world.CommandPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eric on 2/27/2016.
 */
@Serializable
public class StartMultiplayerGameCommand extends AbstractWorldCommand {

    private TeamId playerId;
    private String levelUuid;
    private List<TeamId> allPlayers = new ArrayList<>();

    public StartMultiplayerGameCommand() {
    }

    public StartMultiplayerGameCommand(UUID levelUuid) {
        this.levelUuid = levelUuid.toString();
    }

    public StartMultiplayerGameCommand(StartMultiplayerGameCommand command, TeamId playerId, List<TeamId> allPlayers) {
        this.allPlayers = allPlayers;
        this.levelUuid = command.levelUuid;
        this.playerId = playerId;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        LoadLevelState loadLevelState = commandPackage.getState(LoadLevelState.class);
        LevelSettings levelSettings = new LevelSettings(UUID.fromString(this.levelUuid), this.playerId, this.allPlayers);
        loadLevelState.setLevelToLoad(levelSettings);
        loadLevelState.setEnabled(true);
    }
}
