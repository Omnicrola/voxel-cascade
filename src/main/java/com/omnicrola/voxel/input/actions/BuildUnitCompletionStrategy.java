package com.omnicrola.voxel.input.actions;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.debug.DebugSpatialPositionControl;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by omnic on 3/12/2016.
 */
public class BuildUnitCompletionStrategy implements IBuildCompletionStrategy {
    private int unitId;
    private WorldEntityBuilder worldEntityBuilder;
    private WorldManager worldManager;

    public BuildUnitCompletionStrategy(int unitId,
                                       WorldEntityBuilder worldEntityBuilder,
                                       WorldManager worldManager) {
        this.unitId = unitId;
        this.worldEntityBuilder = worldEntityBuilder;
        this.worldManager = worldManager;
    }

    @Override
    public void build(Vector3f location) {
        // TODO : make team id be the current player
        int teamId = 1;
        UnitPlacement unitPlacement = new UnitPlacement(this.unitId, teamId, location.add(0, 5, 0));
        Unit newUnit = this.worldEntityBuilder.buildUnit(unitPlacement);
        this.worldManager.addUnit(newUnit);
    }

    @Override
    public boolean isAbleToBuild() {
        // TODO : add cost
        return true;
    }
}
