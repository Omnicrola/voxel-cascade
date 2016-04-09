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
    public final LevelData call() throws Exception {
        Logger logger = Logger.getLogger(this.getClass().getName());
        System.out.println("Starting task : "+this.getClass().getName());
        logger.log(Level.FINE, "Started task");
        performLoading();
        logger.log(Level.FINE, "Finished task");
        return this.levelData;
    }

    protected abstract void performLoading();
}
