package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.List;
import java.util.UUID;

/**
 * Created by omnic on 1/24/2016.
 */
public interface IWorldLevelManager {
    public abstract LevelState getCurrentLevel();

    void addObserver(ILevelChangeObserver levelChangeObserver);

    void loadLevel(UUID levelUuid);

    List<TeamStatistics> getTeamStatistics();
}
