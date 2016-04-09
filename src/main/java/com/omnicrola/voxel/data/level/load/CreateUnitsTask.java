package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.UnitDefinition;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.world.build.UnitBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omnic on 4/9/2016.
 */
public class CreateUnitsTask extends AbstractLoadTask {
    private final UnitBuilder unitBuilder;

    public CreateUnitsTask(LevelData levelData, UnitBuilder unitBuilder) {
        super(levelData);
        this.unitBuilder = unitBuilder;
    }

    @Override
    protected void performLoading() {
        List<Unit> structures = levelData.levelDefinition
                .getStructures()
                .stream()
                .map(p -> build(p))
                .collect(Collectors.toList());
        levelData.units = structures;
    }

    private Unit build(UnitPlacement unitPlacement) {
        UnitDefinition unitDefinition = getUnit(unitPlacement);
        return this.unitBuilder.build(unitPlacement, unitDefinition);
    }

    private UnitDefinition getUnit(UnitPlacement structurePlacement) {
        return this.levelData.unitDefinitionRepository.getUnitDefinition(structurePlacement.getUnitId());
    }
}
