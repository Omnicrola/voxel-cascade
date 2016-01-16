package com.omnicrola.voxel.data;

import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelData {

    private Vec3i terrainSize = new Vec3i(40, 5, 40);
    private Vec3i terrainOffset = new Vec3i(0, -5, 0);

    public LevelData() {

    }

    public Vec3i getTerrainOffset() {
        return terrainOffset;
    }

    public Vec3i getTerrainSize() {
        return terrainSize;
    }
}
