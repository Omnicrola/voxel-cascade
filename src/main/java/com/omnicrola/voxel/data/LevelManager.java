package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.CurrentLevelChangeEvent;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.List;

/**
 * Created by Eric on 2/6/2016.
 */
public class LevelManager implements ILevelManager {

    private LevelState currentLevelState;

    public LevelManager() {
    }

    @Override
    public LevelState getCurrentLevel() {
        return this.currentLevelState;
    }

    @Override
    public void setCurrentLevel(LevelState levelState) {
        this.currentLevelState = levelState;
        emitLevelChangeEvent();
    }

    public List<TeamStatistics> getTeamStatistics() {
        return this.currentLevelState.getTeamStatistics();
    }

    private void emitLevelChangeEvent() {
        VoxelEventBus.INSTANCE().post(new CurrentLevelChangeEvent(this.currentLevelState));
    }
}
