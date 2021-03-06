package com.omnicrola.voxel.entities.control.resources;

import com.jme3.math.Vector3f;

/**
 * Created by Eric on 2/11/2016.
 */
public interface IHarvestTarget {
    Vector3f getLocation();

    boolean hasResources();

    float removeResources(float tpf);

    void remove();
}
