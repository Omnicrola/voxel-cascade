package com.omnicrola.voxel.data;

import com.omnicrola.voxel.data.level.LevelState;

/**
 * Created by omnic on 2/11/2016.
 */
public interface ILevelObserver {
    void levelUpdated(LevelState currentLevel);
}
