package com.omnicrola.voxel.world.build;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.StructureDefinition;
import com.omnicrola.voxel.data.units.UnitDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldEntityBuilder {
    private final UnitDefinitionRepository definitionRepository;
    private final AssetManager assetManager;
    private final UnitBuilder unitBuilder;
    private final StructureBuilder structureBuilder;

    public WorldEntityBuilder(UnitDefinitionRepository definitionRepository,
                              AssetManager assetManager,
                              UnitBuilder unitBuilder,
                              StructureBuilder structureBuilder) {
        this.definitionRepository = definitionRepository;
        this.assetManager = assetManager;
        this.unitBuilder = unitBuilder;
        this.structureBuilder = structureBuilder;
    }

    public Unit buildUnit(UnitPlacement unitPlacement) {
        UnitDefinition unitDefinition = getUnitDefinition(unitPlacement.getUnitId());
        return this.unitBuilder.build(unitPlacement, unitDefinition);
    }

    public Spatial buildPlaceholderUnit(int unitId, float buildRadius, SelectionGroup selectionGroup) {
        UnitDefinition unitDefinition = getUnitDefinition(unitId);
        return this.unitBuilder.buildPlaceholder(unitDefinition, buildRadius, selectionGroup);
    }

    public Spatial buildPlaceholderStructure(int globalId, float buildRadius, SelectionGroup selectionGroup) {
        StructureDefinition structureDefinition = getStructureDefinition(globalId);
        return this.structureBuilder.buildPlaceholder(structureDefinition, buildRadius, selectionGroup);
    }

    public Structure buildStructure(UnitPlacement unitPlacement) {
        StructureDefinition structureDefinition = getStructureDefinition(unitPlacement.getUnitId());
        return this.structureBuilder.build(unitPlacement, structureDefinition);
    }

    public Geometry buildCube(ColorRGBA color) {
        Material material = new Material(assetManager, GameConstants.MATERIAL_SHADED);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color);
        material.setColor("Diffuse", color);

        Box box = new Box(0.5f, 0.5f, 0.5f);
        Geometry cube = new Geometry("cube", box);
        cube.setMaterial(material);
        return cube;
    }

    private UnitDefinition getUnitDefinition(int definitionId) {
        UnitDefinition entityDefinition = this.definitionRepository.getUnitDefinition(definitionId);
        if (entityDefinition == UnitDefinition.NONE) {
            throw new IllegalArgumentException("Unit with ID of " + definitionId + " is not defined.");
        }
        return entityDefinition;
    }

    private StructureDefinition getStructureDefinition(int definitionId) {
        StructureDefinition definition = this.definitionRepository.getBuildingDefinition(definitionId);
        if (definition == StructureDefinition.NONE) {
            throw new IllegalArgumentException("Structure with ID of " + definitionId + " is not defined.");
        }
        return definition;
    }

    public float getStructureCost(int globalId) {
        return getStructureDefinition(globalId).getBuildCost();
    }

    public StructureBuilder getStructureBuilder() {
        return structureBuilder;
    }
}
