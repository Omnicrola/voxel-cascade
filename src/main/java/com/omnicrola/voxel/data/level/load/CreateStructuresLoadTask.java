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
    private float totalStructures;
    private float structuresBuilt;

    public CreateStructuresLoadTask(LevelData levelData, StructureBuilder structureBuilder) {
        super(levelData);
        this.structureBuilder = structureBuilder;
    }

    @Override
    protected String getTaskName() {
        return "Create Structures";
    }

    @Override
    protected void performLoading() {
        this.totalStructures = levelData.levelDefinition.getStructures().size();

        List<Structure> structures = levelData.levelDefinition
                .getStructures()
                .stream()
                .map(p -> build(p))
                .collect(Collectors.toList());
        levelData.structures = structures;
    }

    @Override
    public double percentDone() {
        return structuresBuilt / totalStructures;
    }

    private Structure build(UnitPlacement structurePlacement) {
        structurePlacement.setInstanceId(instanceId++);
        StructureDefinition structureDefinition = getStructure(structurePlacement);
        Structure structure = this.structureBuilder.build(structurePlacement, structureDefinition);
        this.structuresBuilt++;
        return structure;

    }

    private StructureDefinition getStructure(UnitPlacement unitPlacement) {
        return levelData.unitDefinitionRepository.getBuildingDefinition(unitPlacement.getUnitId());
    }
}
