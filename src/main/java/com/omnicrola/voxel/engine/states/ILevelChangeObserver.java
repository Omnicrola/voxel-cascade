package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.data.level.LevelState;

/**
 * Created by Eric on 1/26/2016.
 */
public interface ILevelChangeObserver {
    void levelChanged(LevelState currentLevelState);
}
