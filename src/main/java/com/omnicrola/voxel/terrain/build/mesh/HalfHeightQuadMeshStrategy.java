package com.omnicrola.voxel.terrain.build.mesh;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.data.VoxelFace;

/**
 * Created by Eric on 3/10/2016.
 */
public abstract class HalfHeightQuadMeshStrategy implements ITerrainQuadMeshStrategy {
    protected static final float HALF = 0.5f;

    @Override
    public final Vector3f[] build(VoxelFace voxel,
                                  Vector3f bottomLeft,
                                  Vector3f bottomRight,
                                  Vector3f topLeft,
                                  Vector3f topRight) {
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = bottomLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[1] = bottomRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[2] = topLeft.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        vertices[3] = topRight.multLocal(VoxelChunkRebuilder.VOXEL_SIZE);
        if (voxel.isHalf()) {
            vertices[0].subtractLocal(0, offset(0), 0);
            vertices[1].subtractLocal(0, offset(1), 0);
            vertices[2].subtractLocal(0, offset(2), 0);
            vertices[3].subtractLocal(0, offset(3), 0);
        }
        return vertices;
    }

    protected abstract float offset(int vertexIndex);
}
