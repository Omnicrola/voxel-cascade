package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.StructureDefinition;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.world.build.StructureBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 4/8/2016.
 */
public class CreateStructuresLoadTask extends AbstractLoadTask {
    private final StructureBuilder structureBuilder;
    private int instanceId = 1;

    public CreateStructuresLoadTask(LevelData levelData, StructureBuilder structureBuilder) {
        super(levelData);
        this.structureBuilder = structureBuilder;
    }

    @Override
    protected void performLoading() {
        List<Structure> structures = levelData.levelDefinition
                .getStructures()
                .stream()
                .map(p -> build(p))
                .collect(Collectors.toList());
        levelData.structures = structures;
    }

    private Structure build(UnitPlacement structurePlacement) {
        structurePlacement.setInstanceId(instanceId++);
        StructureDefinition structureDefinition = getStructure(structurePlacement);
        return this.structureBuilder.build(structurePlacement, structureDefinition);
    }

    private StructureDefinition getStructure(UnitPlacement unitPlacement) {
        return levelData.unitDefinitionRepository.getBuildingDefinition(unitPlacement.getUnitId());
    }
}
