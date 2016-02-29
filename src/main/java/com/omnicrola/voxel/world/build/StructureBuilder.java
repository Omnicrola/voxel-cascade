package com.omnicrola.voxel.world.build;

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.StructureDefinition;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by Eric on 2/28/2016.
 */
public class StructureBuilder {

    WorldBuilderToolbox toolbox;

    public StructureBuilder(WorldBuilderToolbox toolbox) {
        this.toolbox = toolbox;
    }

    public Structure build(UnitPlacement unitPlacement, StructureDefinition structureDefinition) {
        Spatial spatial = toolbox.getModel(structureDefinition.getModel());
        spatial.setName(structureDefinition.getName());
        Material material = toolbox.createMaterial(structureDefinition.getTexture());
        spatial.setMaterial(material);

        toolbox.runFactories(spatial, structureDefinition.getControlFactories());
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
