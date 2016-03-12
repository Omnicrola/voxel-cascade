package com.omnicrola.voxel.terrain;

import com.omnicrola.voxel.terrain.build.mesh.HalfHeightQuadMeshStrategy;

/**
 * Created by Eric on 3/12/2016.
 */
public class QuadMeshStrategyYPos extends HalfHeightQuadMeshStrategy {

    @Override
    protected float offset(int vertexIndex) {
        return HALF;
    }
}
