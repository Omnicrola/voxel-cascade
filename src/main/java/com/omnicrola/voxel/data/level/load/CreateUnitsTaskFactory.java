package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.world.build.UnitBuilder;

import java.util.concurrent.Callable;

/**
 * Created by Eric on 4/8/2016.
 */
public class CreateUnitsTaskFactory implements ILoadingTaskFactory {

    UnitBuilder unitBuilder;

    public CreateUnitsTaskFactory(UnitBuilder unitBuilder) {
        this.unitBuilder = unitBuilder;
    }

    @Override
    public Callable<LevelData> build(LevelData levelData) {
        return new CreateUnitsTask(levelData, unitBuilder);
    }
}
