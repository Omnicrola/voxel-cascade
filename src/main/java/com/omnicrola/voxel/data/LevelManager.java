package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.CurrentLevelChangeEvent;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.List;
import java.util.UUID;

/**
 * Created by Eric on 2/6/2016.
 */
public class LevelManager implements ILevelManager {

    private LevelState currentLevelState;
    private LevelDefinitionRepository levelDefinitionRepository;

    public LevelManager(LevelDefinitionRepository levelDefinitionRepository) {
        this.levelDefinitionRepository = levelDefinitionRepository;
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

    @Override
    public List<LevelDefinition> getAllLevels() {
        return this.levelDefinitionRepository.getAllLevels();
    }

    @Override
    public LevelDefinition getLevel(UUID levelGlobalId) {
        return this.levelDefinitionRepository.getLevel(levelGlobalId);
    }

    private void emitLevelChangeEvent() {
        VoxelEventBus.INSTANCE().post(new CurrentLevelChangeEvent(this.currentLevelState));
    }
}
