package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;

import java.util.List;

/**
 * Created by Eric on 3/19/2016.
 */
@Serializable
public class OrderAttackTargetCommand extends AbstractWorldCommand {
    private int targetId;
    private int[] unitIds;

    public OrderAttackTargetCommand() {
    }

    public OrderAttackTargetCommand(int targetId, int[] unitIds) {
        this.targetId = targetId;
        this.unitIds = unitIds;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldManager worldManager = commandPackage.getWorldManager();
        List<Spatial> units = worldManager.selectEntities(this.unitIds);
        Spatial spatial = worldManager.selectEntity(this.targetId);
        EntityCommandAdapter entityCommandAdapter = commandPackage.getEntityCommandAdapter();
        entityCommandAdapter.orderAttackTarget(units, spatial);
    }
}
