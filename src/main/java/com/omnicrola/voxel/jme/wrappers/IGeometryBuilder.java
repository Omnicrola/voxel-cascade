package com.omnicrola.voxel.jme.wrappers;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IGeometryBuilder {
    Geometry cube(float size, ColorRGBA color);

    Geometry box(float width, float height, float depth, ColorRGBA color);

    Geometry cylinder(float radius, float height, ColorRGBA color);

    Geometry sphere(float radius, ColorRGBA colorRGBA);

}
