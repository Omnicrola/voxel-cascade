package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Optional;

/**
 * Created by Eric on 2/24/2016.
 */
public interface ITerrainManager {

    void load(TerrainDefinition terrain);

    void setVoxel(Vec3i location, byte voxelType);

    VoxelData getVoxelAt(Vector3f location);

    VoxelData getVoxelAt(Vec3i location);

    void globalRebuild();

    Optional<VoxelData> findLowestEmptyVoxel(Vector3f location);

    Optional<VoxelData> getHighestSolidVoxel(Vector3f location);

    IVoxelType getVoxelType(byte voxelType);

}
