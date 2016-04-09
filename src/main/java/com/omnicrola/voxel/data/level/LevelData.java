package com.omnicrola.voxel.data.level;

import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 4/8/2016.
 */
public class LevelData {
    public final LevelDefinition levelDefinition;
    public final UnitDefinitionRepository unitDefinitionRepository;
    public List<Structure> structures = new ArrayList<>();

    public LevelData(LevelDefinition levelDefinition, UnitDefinitionRepository unitDefinitionRepository) {
        this.levelDefinition = levelDefinition;
        this.unitDefinitionRepository = unitDefinitionRepository;
    }
}
