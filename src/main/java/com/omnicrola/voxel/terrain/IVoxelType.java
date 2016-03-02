package com.omnicrola.voxel.terrain;

import com.jme3.math.ColorRGBA;

/**
 * Created by omnic on 2/2/2016.
 */
public interface IVoxelType {
    byte uniqueId();

    ColorRGBA color();
}
