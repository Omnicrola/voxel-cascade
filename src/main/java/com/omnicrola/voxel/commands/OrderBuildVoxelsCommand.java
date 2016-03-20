package com.omnicrola.voxel.commands;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.entities.control.construction.VoxelConstructionPackage;
import com.omnicrola.voxel.entities.control.resources.VoxelDataHarvestComparator;
import com.omnicrola.voxel.entities.control.resources.VoxelQueue;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.world.CommandPackage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 3/20/2016.
 */
@Serializable
public class OrderBuildVoxelsCommand extends AbstractWorldCommand {
    private int[] unitIds;
    private List<Vec3i> selectedVoxels;
    private byte voxelType;

    public OrderBuildVoxelsCommand(int[] unitIds, List<Vec3i> selectedVoxels, byte voxelType) {
        this.unitIds = unitIds;
        this.selectedVoxels = selectedVoxels;
        this.voxelType = voxelType;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        ITerrainManager terrainManager = commandPackage.getTerrainManager();
        List<VoxelData> voxels = this.selectedVoxels.stream()
                .map(vec -> terrainManager.getVoxelAt(vec))
                .collect(Collectors.toList());
        VoxelQueue voxelQueue = new VoxelQueue(voxels, new VoxelDataHarvestComparator(new Vector3f()));

        VoxelConstructionPackage voxelConstructionPackage = new VoxelConstructionPackage(
                terrainManager,
                this.voxelType,
                voxelQueue);

        List<Spatial> units = commandPackage.getWorldManager().selectEntities(this.unitIds);
        commandPackage.getEntityCommandAdapter().orderBuild(units, voxelConstructionPackage);

    }
}
