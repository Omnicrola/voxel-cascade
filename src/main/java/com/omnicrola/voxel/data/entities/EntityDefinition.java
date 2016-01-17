package com.omnicrola.voxel.data.entities;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityDefinition {

    private Vector3f placement;
    private ColorRGBA color;
    private String modelTexture;
    private String modelGeometry;
    private int hitpoints;


    public EntityDefinition() {
    }

    public EntityDefinition(Vector3f placement, ColorRGBA color) {
        this.placement = placement;
        this.color = color;
    }

    public ColorRGBA getColor() {
        return color;
    }

    public Vector3f getPlacement() {
        return placement;
    }
}
