package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;

import java.util.concurrent.Callable;

/**
 * Created by Eric on 4/8/2016.
 */
public abstract class AbstractLoadTask implements Callable<LevelData> {
    protected LevelData levelData;

    public AbstractLoadTask(LevelData levelData) {
        this.levelData = levelData;
    }

    @Override
    public final LevelData call() throws Exception {
        performLoading();
        return this.levelData;
    }

    protected abstract void performLoading();
}
