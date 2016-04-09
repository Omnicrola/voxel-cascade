package com.omnicrola.voxel.data.level;

import com.omnicrola.voxel.data.units.UnitDefinitionRepository;

/**
 * Created by Eric on 4/8/2016.
 */
public class LevelData {
    public final UnitDefinitionRepository unitDefinitionRepository;

    public LevelData(UnitDefinitionRepository unitDefinitionRepository) {
        this.unitDefinitionRepository = unitDefinitionRepository;
    }
}
