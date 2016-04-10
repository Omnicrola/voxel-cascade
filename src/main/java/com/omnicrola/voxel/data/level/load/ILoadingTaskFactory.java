package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;

/**
 * Created by Eric on 4/8/2016.
 */
public interface ILoadingTaskFactory {
    AbstractLoadTask build(LevelData levelData);
}
