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
public class CreateUnitsLoadTask extends AbstractLoadTask {
    private final UnitBuilder unitBuilder;
    private int unitInstanceId = 1;
    private float totalUnits;
    private float unitsBuilt;

    public CreateUnitsLoadTask(LevelData levelData, UnitBuilder unitBuilder) {
        super(levelData);
        this.unitBuilder = unitBuilder;
    }

    @Override
    protected String getTaskName() {
        return "Create Units";
    }

    @Override
    public double percentDone() {
        return unitsBuilt / totalUnits;
    }

    @Override
    protected void performLoading() {
        this.totalUnits = levelData.levelDefinition.getUnitPlacements().size();

        List<Unit> structures = levelData.levelDefinition
                .getUnitPlacements()
                .stream()
                .map(p -> build(p))
                .collect(Collectors.toList());
        levelData.units = structures;
    }

    private Unit build(UnitPlacement unitPlacement) {
        unitPlacement.setInstanceId(unitInstanceId++);
        UnitDefinition unitDefinition = getUnit(unitPlacement);
        Unit unit = this.unitBuilder.build(unitPlacement, unitDefinition);
        this.unitsBuilt++;
        return unit;
    }

    private UnitDefinition getUnit(UnitPlacement structurePlacement) {
        UnitDefinition unitDefinition = this.levelData.unitDefinitionRepository.getUnitDefinition(structurePlacement.getUnitId());
        return unitDefinition;
    }
}
