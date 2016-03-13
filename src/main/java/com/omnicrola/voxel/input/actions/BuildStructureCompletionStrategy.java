package com.omnicrola.voxel.input.actions;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by omnic on 3/12/2016.
 */
public class BuildStructureCompletionStrategy implements IBuildCompletionStrategy {
    private final int globalId;
    private final WorldEntityBuilder worldEntityBuilder;
    private final WorldManager worldManager;

    public BuildStructureCompletionStrategy(int globalId, WorldEntityBuilder worldEntityBuilder, WorldManager worldManager) {
        this.globalId = globalId;
        this.worldEntityBuilder = worldEntityBuilder;
        this.worldManager = worldManager;
    }

    @Override
    public void build(Vector3f location) {
        // TODO : make team id be the current player
        int teamId = 1;
        location.setX((int) location.x);
        location.setZ((int) location.z);
        UnitPlacement unitPlacement = new UnitPlacement(this.globalId, teamId, location);
        Structure structure = this.worldEntityBuilder.buildStructure(unitPlacement);
        worldManager.addStructure(structure);
    }

    @Override
    public boolean isAbleToBuild() {

        return true;
    }
}
