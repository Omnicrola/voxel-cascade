package com.omnicrola.voxel.terrain.build;

import com.omnicrola.util.Vec3i;

/**
 * Created by Eric on 2/14/2016.
 */
public class NullOcclusionCalculator implements IOcclusionCalculator{
    public static final NullOcclusionCalculator NO_OP = new NullOcclusionCalculator();

    private NullOcclusionCalculator() {
    }

    @Override
    public OcclusionSet calculate(Vec3i globalLocation) {
        return new OcclusionSet(){
            @Override
            public float vertexValue(int index) {
                return 1f;
            }
        };
    }
}
