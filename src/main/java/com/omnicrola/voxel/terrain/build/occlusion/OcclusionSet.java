package com.omnicrola.voxel.terrain.build.occlusion;

import java.util.Arrays;

/**
 * Created by Eric on 2/13/2016.
 */
public class OcclusionSet {

    private final int[] vertexOcclusion;

    public OcclusionSet() {
        this.vertexOcclusion = new int[4];
    }

    public void set(int index, int value) {
        this.vertexOcclusion[index] = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OcclusionSet)) return false;

        OcclusionSet that = (OcclusionSet) o;

        return Arrays.equals(vertexOcclusion, that.vertexOcclusion);

    }

    @Override
    public int hashCode() {
        return vertexOcclusion != null ? Arrays.hashCode(vertexOcclusion) : 0;
    }

    public float vertexValue(int index) {
        return 1f - (this.vertexOcclusion[index] / 5f);
    }
}
