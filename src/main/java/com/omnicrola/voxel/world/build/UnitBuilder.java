package com.omnicrola.voxel.world.build;

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.data.units.UnitDefinition;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by Eric on 2/28/2016.
 */
public class UnitBuilder {

    WorldBuilderToolbox toolbox;

    public UnitBuilder(WorldBuilderToolbox toolbox) {
        this.toolbox = toolbox;
    }

    public Unit build(UnitPlacement unitPlacement, UnitDefinition unitDefinition) {
        TeamData teamData = toolbox.getTeamData(unitPlacement.getTeamId());
        Spatial spatial = toolbox.getModel(unitDefinition.getModel());
        spatial.setName(unitDefinition.getName());
        Material material = toolbox.createMaterial(unitDefinition.getTexture());
        spatial.setMaterial(material);

        toolbox.runFactories(spatial, unitDefinition.getControlFactories());

        spatial.setUserData(EntityDataKeys.IS_SELECTABLE, true);
        spatial.setUserData(EntityDataKeys.IS_TARGETABLE, true);
        spatial.setUserData(EntityDataKeys.IS_COLLIDABLE, true);
        spatial.setUserData(EntityDataKeys.IS_UNIT, true);
        spatial.setUserData(EntityDataKeys.HARVEST_RANGE, 2f);
        spatial.setUserData(EntityDataKeys.HITPOINTS, unitDefinition.getHitpoints());
        spatial.setUserData(EntityDataKeys.TEAM_DATA, teamData);
        return new Unit(spatial);

    }

}
