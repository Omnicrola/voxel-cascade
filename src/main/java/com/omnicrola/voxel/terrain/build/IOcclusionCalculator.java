package com.omnicrola.voxel.terrain.build;

import com.omnicrola.util.Vec3i;

/**
 * Created by Eric on 2/14/2016.
 */
public interface IOcclusionCalculator {
    OcclusionSet calculate(Vec3i globalLocation);
}
