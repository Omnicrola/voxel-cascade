package com.omnicrola.voxel.world.build;

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.StructureDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.entities.Structure;
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
public class StructureBuilder {

    private WorldBuilderToolbox toolbox;
    private EntityControlAdapter entityControlAdapter;
    private MaterialRepository materialRepository;

    public StructureBuilder(WorldBuilderToolbox toolbox, EntityControlAdapter entityControlAdapter, MaterialRepository materialRepository) {
        this.toolbox = toolbox;
        this.entityControlAdapter = entityControlAdapter;
        this.materialRepository = materialRepository;
    }

    public Structure build(UnitPlacement unitPlacement, StructureDefinition structureDefinition) {
        Spatial spatial = buildSpatial(structureDefinition);

        List<IControlFactory> controlFactories = structureDefinition.getControlFactories();
        UnitDefinitionRepository definitionRepository = toolbox.getDefinitionRepository();
        controlFactories.forEach(f -> f.build(spatial, definitionRepository, this.entityControlAdapter));

        spatial.setLocalTranslation(unitPlacement.getLocation());
        TeamId teamId = TeamId.create(unitPlacement.getTeamId());

        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_STRUCTURE, true);
        spatial.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        spatial.setUserData(EntityDataKeys.IS_TARGETABLE, true);
        spatial.setUserData(EntityDataKeys.HITPOINTS, structureDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.MAX_HITPOINTS, structureDefinition.getHitpoints());

        spatial.setUserData(EntityDataKeys.WORLD_ID, unitPlacement.getInstanceId());
        spatial.setUserData(EntityDataKeys.TEAM_ID, teamId);


        return new Structure(spatial);
    }

    private Spatial buildSpatial(StructureDefinition structureDefinition) {
        Spatial spatial = toolbox.getModel(structureDefinition.getModel());
        spatial.setName(structureDefinition.getName());
        Material material = materialRepository.createMaterial(structureDefinition.getTexture());
        spatial.setMaterial(material);
        return spatial;
    }

    public Spatial buildPlaceholder(StructureDefinition structureDefinition, float buildRadius, SelectionGroup selectionGroup) {
        Spatial spatial = buildSpatial(structureDefinition);
        Material validMaterial = materialRepository.getMaterial(MaterialToken.BUILD_VALID);
        Material invalidMaterial = materialRepository.getMaterial(MaterialToken.BUILD_NOT_VALID);
        BuildCursorValidityControl buildCursorValidityControl = new BuildCursorValidityControl(selectionGroup, buildRadius, validMaterial, invalidMaterial);
        spatial.addControl(buildCursorValidityControl);
        return spatial;
    }
}
