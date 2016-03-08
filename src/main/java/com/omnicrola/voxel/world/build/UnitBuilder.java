package com.omnicrola.voxel.world.build;

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.UnitDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.construction.BuildCursorValidityControl;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.settings.EntityDataKeys;

import java.util.List;

/**
 * Created by Eric on 2/28/2016.
 */
public class UnitBuilder {

    WorldBuilderToolbox toolbox;
    private EntityControlAdapter entityControlAdapter;
    private MaterialRepository materialRepository;

    public UnitBuilder(WorldBuilderToolbox toolbox,
                       EntityControlAdapter entityControlAdapter,
                       MaterialRepository materialRepository) {
        this.toolbox = toolbox;
        this.entityControlAdapter = entityControlAdapter;
        this.materialRepository = materialRepository;
    }

    public Unit build(UnitPlacement unitPlacement, UnitDefinition unitDefinition) {
        TeamData teamData = toolbox.getTeamData(unitPlacement.getTeamId());
        Spatial spatial = buildSpatial(unitDefinition);

        List<IControlFactory> controlFactories = unitDefinition.getControlFactories();
        UnitDefinitionRepository definitionRepository = toolbox.getDefinitionRepository();
        controlFactories.forEach(f -> f.build(spatial, definitionRepository, this.entityControlAdapter));
        spatial.setLocalTranslation(unitPlacement.getLocation());


        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_TARGETABLE, true);
        spatial.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        spatial.setUserData(EntityDataKeys.IS_UNIT, true);
        spatial.setUserData(EntityDataKeys.HARVEST_RANGE, 2f);
        spatial.setUserData(EntityDataKeys.HITPOINTS, unitDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, teamData);
        return new Unit(spatial);

    }

    public Spatial buildPlaceholder(UnitDefinition unitDefinition, float buildRadius, SelectionGroup selectionGroup) {
        Spatial spatial = buildSpatial(unitDefinition);
        Material validMaterial = materialRepository.getMaterial(MaterialToken.BUILD_VALID);
        Material invalidMaterial = materialRepository.getMaterial(MaterialToken.BUILD_NOT_VALID);
        BuildCursorValidityControl buildCursorValidityControl = new BuildCursorValidityControl(selectionGroup, buildRadius, validMaterial, invalidMaterial);
        spatial.addControl(buildCursorValidityControl);
        return spatial;
    }

    private Spatial buildSpatial(UnitDefinition unitDefinition) {
        Spatial spatial = toolbox.getModel(unitDefinition.getModel());
        spatial.setName(unitDefinition.getName());
        Material material = materialRepository.createMaterial(unitDefinition.getTexture());
        spatial.setMaterial(material);
        return spatial;
    }

}
