package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelDefinition;

import java.util.concurrent.Callable;

/**
 * Created by Eric on 4/8/2016.
 */
public interface ILoadingTaskFactory {
    Callable<LevelData> build(LevelData levelData);
}
