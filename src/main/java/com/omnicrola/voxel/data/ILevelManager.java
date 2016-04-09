package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.List;

/**
 * Created by omnic on 1/24/2016.
 */
public interface ILevelManager {
    LevelState getCurrentLevel();

    void setCurrentLevel(LevelState levelState);

    List<TeamStatistics> getTeamStatistics();
}
