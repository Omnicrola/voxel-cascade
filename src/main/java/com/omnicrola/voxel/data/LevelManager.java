package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.*;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.CurrentLevelChangeEvent;
import com.omnicrola.voxel.ui.data.TeamStatistics;
import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/6/2016.
 */
public class LevelManager implements ILevelManager {
    private static final Logger LOGGER = Logger.getLogger(LevelManager.class.getName());

    private final LevelDefinitionRepository levelDefinitions;
    private LevelLoadingAdapter levelLoadingAdapter;
    private LevelState currentLevelState;

    public LevelManager(@NotNull LevelDefinitionRepository levelDefinitions,
                        @NotNull LevelLoadingAdapter levelLoadingAdapter) {
        this.levelDefinitions = levelDefinitions;
        this.levelLoadingAdapter = levelLoadingAdapter;
    }

    @Override
    public void loadLevel(@NotNull UUID levelId) {
        LOGGER.log(Level.INFO, "Loading level : " + levelId);
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        loadLevel(levelDefinition);
    }

    public void loadLevel(@NotNull LevelDefinition newLevelDefinition) {
        LOGGER.log(Level.INFO, "Loading level : " + newLevelDefinition.getName());
        if (this.currentLevelState != null) {
            this.currentLevelState.dispose();
        }
        LevelStateLoader levelStateLoader = this.levelLoadingAdapter.getLoader();
        this.currentLevelState = levelStateLoader.create(newLevelDefinition);
        emitLevelChangeEvent();
    }


    private void emitLevelChangeEvent() {
        VoxelEventBus.INSTANCE().post(new CurrentLevelChangeEvent(this.currentLevelState));
    }

    @Override
    public LevelState getCurrentLevel() {
        return this.currentLevelState;
    }

    public List<TeamStatistics> getTeamStatistics() {
        return this.currentLevelState.getTeamStatistics();
    }

}
