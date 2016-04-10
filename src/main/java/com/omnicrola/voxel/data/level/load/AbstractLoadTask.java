package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 4/8/2016.
 */
public abstract class AbstractLoadTask implements Callable<LevelData> {
    protected LevelData levelData;

    public AbstractLoadTask(LevelData levelData) {
        this.levelData = levelData;
    }

    @Override
    public final LevelData call() {
        Logger logger = Logger.getLogger(this.getClass().getName());
        try {
            logger.log(Level.FINE, "Started task "+getTaskName());
            performLoading();
            logger.log(Level.FINE, "Finished task "+ getTaskName());
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return this.levelData;
    }

    protected abstract String getTaskName();

    protected abstract void performLoading();
}
