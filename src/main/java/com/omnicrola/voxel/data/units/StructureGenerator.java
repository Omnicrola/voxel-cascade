package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.entities.commands.BuildUnitCommand;
import com.omnicrola.voxel.entities.commands.EntityCommand;

/**
 * Created by omnic on 1/24/2016.
 */
public class StructureGenerator {
    public static final int BUILDING_CORE_ID = 1;

    public static StructureDefinition createCoreBuilding() {
        StructureDefinition structureDefinition = new StructureDefinition();
        structureDefinition.globalId = BUILDING_CORE_ID;
        structureDefinition.color = ColorRGBA.Blue;
        structureDefinition.modelGeometry = "building-core.obj";
        structureDefinition.modelTexture = "voxel-face-green.png";
        structureDefinition.name = "Core";
        structureDefinition.description = "Use this to build things";
        structureDefinition.hitpoints = 500;
        structureDefinition.commands.add(EntityCommand.BUILD);
        structureDefinition.buildCommands.add(new BuildUnitCommand(UnitGenerator.DEFAULT_TANK_ID));
        return structureDefinition;
    }
}
