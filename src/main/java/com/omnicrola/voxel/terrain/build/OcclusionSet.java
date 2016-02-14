package com.omnicrola.voxel.terrain.build;

import java.util.Arrays;

/**
 * Created by Eric on 2/13/2016.
 */
public class OcclusionSet {

    private final byte[] vertexOcclusion;

    public OcclusionSet() {
        this.vertexOcclusion = new byte[4];
    }

    public void increment(int index) {
        this.vertexOcclusion[index]++;
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
}
