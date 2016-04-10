package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.world.build.StructureBuilder;

/**
 * Created by Eric on 4/8/2016.
 */
public class CreateStructuresTaskFactory implements ILoadingTaskFactory {
    private StructureBuilder structureBuilder;

    public CreateStructuresTaskFactory(StructureBuilder structureBuilder) {
        this.structureBuilder = structureBuilder;
    }

    @Override
    public AbstractLoadTask build(LevelData levelData) {
        return new CreateStructuresLoadTask(levelData, structureBuilder);
    }
}
