package com.omnicrola.voxel.data.level;

import com.omnicrola.voxel.data.TeamId;

import java.util.List;
import java.util.UUID;

/**
 * Created by omnic on 4/9/2016.
 */
public class LevelSettings {
    private final UUID levelId;
    private final TeamId playerId;
    private List<TeamId> allPlayers;

    public LevelSettings(UUID levelId, TeamId playerId, List<TeamId> allPlayers) {
        this.levelId = levelId;
        this.playerId = playerId;
        this.allPlayers = allPlayers;
    }

    public UUID getLevelId() {
        return this.levelId;
    }

    public TeamId getPlayerTeamId() {
        return this.playerId;
    }

    public List<TeamId> getAllPlayers() {
        return allPlayers;
    }
}
