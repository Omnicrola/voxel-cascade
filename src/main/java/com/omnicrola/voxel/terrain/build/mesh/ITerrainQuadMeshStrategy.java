package com.omnicrola.voxel.terrain.build.mesh;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.data.VoxelFace;

/**
 * Created by Eric on 3/10/2016.
 */
public interface ITerrainQuadMeshStrategy {
    Vector3f[] build(VoxelFace voxel, Vector3f bottomLeft, Vector3f bottomRight, Vector3f topLeft, Vector3f topRight);
}
