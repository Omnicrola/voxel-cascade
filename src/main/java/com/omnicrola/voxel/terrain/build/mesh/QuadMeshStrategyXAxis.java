package com.omnicrola.voxel.terrain.build.mesh;

/**
 * Created by Eric on 3/12/2016.
 */
public class QuadMeshStrategyXAxis extends HalfHeightQuadMeshStrategy {

    private static final float[] offsets = new float[]{0f, 0f, HALF, HALF};

    @Override
    protected float offset(int vertexIndex) {
        return offsets[vertexIndex];
    }
}
