package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by Eric on 2/24/2016.
 */
public interface ITerrainManager {
    void globalReset();

    void load(TerrainDefinition terrain);

    void setVoxel(Vec3i location, byte voxelType);

    VoxelData getVoxelAt(Vector3f location);

    boolean isBelowTerrain(Geometry geometry);

    Vector3f getLowestNonSolidVoxel(Vector3f location);

    void globalRebuild();
}
