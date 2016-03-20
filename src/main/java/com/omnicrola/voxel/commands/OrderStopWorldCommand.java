package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;

import java.util.List;

/**
 * Created by Eric on 3/20/2016.
 */
@Serializable
public class OrderStopWorldCommand extends AbstractWorldCommand {
    private int[] unitIds;

    public OrderStopWorldCommand() {
    }

    public OrderStopWorldCommand(int[] unitIds) {
        this.unitIds = unitIds;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldManager worldManager = commandPackage.getWorldManager();
        List<Spatial> units = worldManager.selectEntities(unitIds);
        EntityCommandAdapter entityCommandAdapter = commandPackage.getEntityCommandAdapter();
        entityCommandAdapter.orderStop(units);
    }
}
