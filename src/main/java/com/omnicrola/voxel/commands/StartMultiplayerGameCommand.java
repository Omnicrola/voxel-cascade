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
    private int selectedTeam;

    public StartMultiplayerGameCommand() {
    }

    public StartMultiplayerGameCommand(UUID levelUuid ) {
        this.selectedTeam = selectedTeam;
        this.levelUuid = levelUuid.toString();
    }

    public StartMultiplayerGameCommand(StartMultiplayerGameCommand command, TeamId playerTeam, List<TeamId> allTeams) {
        this.allPlayers = allTeams;
        this.levelUuid = command.levelUuid;
        this.playerId = playerTeam;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        LoadLevelState loadLevelState = commandPackage.getState(LoadLevelState.class);
        LevelSettings levelSettings = new LevelSettings(UUID.fromString(this.levelUuid), this.playerId, this.allPlayers);
        loadLevelState.setLevelToLoad(levelSettings);
        loadLevelState.setEnabled(true);
    }
}
