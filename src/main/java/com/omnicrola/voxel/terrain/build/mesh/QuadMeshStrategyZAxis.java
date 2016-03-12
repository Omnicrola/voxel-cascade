package com.omnicrola.voxel.terrain.build.mesh;

/**
 * Created by Eric on 3/12/2016.
 */
public class QuadMeshStrategyZAxis extends HalfHeightQuadMeshStrategy {

    private static final float[] offsets = new float[]{0f, HALF, 0f, HALF};

    @Override
    protected float offset(int vertexIndex) {
        return offsets[vertexIndex];
    }
}
