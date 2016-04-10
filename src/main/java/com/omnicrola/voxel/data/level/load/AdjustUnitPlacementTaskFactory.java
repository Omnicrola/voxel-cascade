package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;

/**
 * Created by omnic on 4/9/2016.
 */
public class AdjustUnitPlacementTaskFactory implements ILoadingTaskFactory {
    @Override
    public AbstractLoadTask build(LevelData levelData) {
        return new AdjustUnitPlacementLoadTask(levelData);
    }
}
