package com.omnicrola.voxel.commands;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.entities.control.resources.VoxelDataHarvestComparator;
import com.omnicrola.voxel.entities.control.resources.VoxelHarvestTarget;
import com.omnicrola.voxel.entities.control.resources.VoxelQueue;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 3/19/2016.
 */
@Serializable
public class OrderHarvestVoxelsCommand extends AbstractWorldCommand {
    private int[] unitIds;
    private List<Vec3i> voxels;

    public OrderHarvestVoxelsCommand() {
    }

    public OrderHarvestVoxelsCommand(int[] unitIds, List<Vec3i> voxels) {
        this.unitIds = unitIds;
        this.voxels = voxels;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldManager worldManager = commandPackage.getWorldManager();
        List<Spatial> units = worldManager.selectEntities(this.unitIds);

        ITerrainManager terrainManager = commandPackage.getTerrainManager();
        List<VoxelData> voxels = this.voxels.stream()
                .map(vec -> terrainManager.getVoxelAt(vec))
                .collect(Collectors.toList());
        VoxelQueue voxelQueue = new VoxelQueue(voxels, new VoxelDataHarvestComparator(new Vector3f()));

        VoxelHarvestTarget voxelHarvestTarget = new VoxelHarvestTarget(voxelQueue, commandPackage.getParticleBuilder(), worldManager);

        EntityCommandAdapter entityCommandAdapter = commandPackage.getEntityCommandAdapter();
        entityCommandAdapter.orderHarvest(units, voxelHarvestTarget);
    }
}
