package com.omnicrola.voxel.world.build;

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.StructureDefinition;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;
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
        Spatial spatial = toolbox.getModel(structureDefinition.getModel());
        spatial.setName(structureDefinition.getName());
        Material material = materialRepository.createMaterial(structureDefinition.getTexture());
        spatial.setMaterial(material);

        List<IControlFactory> controlFactories = structureDefinition.getControlFactories();
        UnitDefinitionRepository definitionRepository = toolbox.getDefinitionRepository();
        controlFactories.forEach(f -> f.build(spatial, definitionRepository, this.entityControlAdapter));

        TeamData teamData = toolbox.getTeamData(unitPlacement.getTeamId());

        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_STRUCTURE, true);
        spatial.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        spatial.setUserData(EntityDataKeys.IS_TARGETABLE, true);
        spatial.setUserData(EntityDataKeys.HITPOINTS, structureDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, teamData);

        return new Structure(spatial);

    }
}