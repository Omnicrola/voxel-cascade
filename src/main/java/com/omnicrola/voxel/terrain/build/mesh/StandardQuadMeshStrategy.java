package com.omnicrola.voxel.terrain.build.mesh;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.data.VoxelFace;

/**
 * Created by Eric on 3/10/2016.
 */
public class StandardQuadMeshStrategy implements ITerrainQuadMeshStrategy {
    @Override
    public Vector3f[] build(VoxelFace voxel,
                            Vector3f bottomLeft,
                            Vector3f bottomRight,
                            Vector3f topLeft,
                            Vector3f topRight) {
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = bottomLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[1] = bottomRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[2] = topLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[3] = topRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        return vertices;
    }
}
