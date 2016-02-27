package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.LevelStateLoader;
import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eric on 2/6/2016.
 */
public class LevelManager implements ILevelManager {
    private final LevelDefinitionRepository levelDefinitions;
    private final LevelStateLoader levelStateLoader;
    private LevelState currentLevelState;
    private ArrayList<ILevelChangeObserver> observers;

    public LevelManager(LevelDefinitionRepository levelDefinitions, LevelStateLoader levelStateLoader) {
        this.levelDefinitions = levelDefinitions;
        this.levelStateLoader = levelStateLoader;
        this.observers = new ArrayList<>();

    }

    @Override
    public void loadLevel(UUID levelId) {
        LevelDefinition levelDefinition = levelDefinitions.getLevel(levelId);
        loadLevel(levelDefinition);
    }

    public void loadLevel(LevelDefinition newLevelDefinition) {
        if (this.currentLevelState != null) {
            this.currentLevelState.dispose();
        }
        this.currentLevelState = this.levelStateLoader.create(newLevelDefinition);
        notifyObserversOfLevelChange();
    }


    private void notifyObserversOfLevelChange() {
        this.observers.forEach(o -> o.levelChanged(this.currentLevelState));
    }

    @Override
    public LevelState getCurrentLevel() {
        return this.currentLevelState;
    }

    @Override
    public void addObserver(ILevelChangeObserver levelChangeObserver) {
        this.observers.add(levelChangeObserver);
    }

    public List<TeamStatistics> getTeamStatistics() {
        return this.currentLevelState.getTeamStatistics();
    }
}
