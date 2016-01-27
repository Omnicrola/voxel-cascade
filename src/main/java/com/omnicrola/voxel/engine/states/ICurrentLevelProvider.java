package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.data.level.LevelState;

/**
 * Created by omnic on 1/24/2016.
 */
public interface ICurrentLevelProvider {
    public abstract LevelState getCurrentLevelState();

    void subscribe(ILevelChangeObserver levelChangeObserver);
}
