package com.omnicrola.voxel.commands;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;

import java.util.List;

/**
 * Created by Eric on 3/19/2016.
 */
@Serializable
public class OrderAttackLocationCommand extends AbstractWorldCommand {
    private Vector3f terrainLocation;
    private int[] unitIds;

    public OrderAttackLocationCommand() {
    }

    public OrderAttackLocationCommand(Vector3f terrainLocation, int[] unitIds) {
        this.terrainLocation = terrainLocation;
        this.unitIds = unitIds;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldManager worldManager = commandPackage.getWorldManager();
        EntityCommandAdapter entityCommandAdapter = commandPackage.getEntityCommandAdapter();
        List<Spatial> units = worldManager.selectEntities(this.unitIds);
        entityCommandAdapter.orderAttackLocation(units, this.terrainLocation);
    }
}
