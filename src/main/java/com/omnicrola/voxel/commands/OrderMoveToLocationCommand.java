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
public class OrderMoveToLocationCommand extends AbstractWorldCommand {

    private Vector3f location;
    private int[] unitIds;

    public OrderMoveToLocationCommand() {
    }

    public OrderMoveToLocationCommand(Vector3f location, int[] unitIds) {
        this.location = location;
        this.unitIds = unitIds;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        EntityCommandAdapter entityCommandAdapter = commandPackage.getEntityCommandAdapter();
        WorldManager worldManager = commandPackage.getWorldManager();
        List<Spatial> selectedUnits = worldManager.selectEntities(this.unitIds);
        entityCommandAdapter.orderMoveToLocation(location, selectedUnits);
    }
}
