package com.omnicrola.voxel.data.entities;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.control.CollisionControlFactory;
import com.omnicrola.voxel.entities.control.EntityAiControlFactory;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.entities.control.PhysicsControlFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityDefinition {

    private Vector3f placement;
    private ColorRGBA color;
    private String modelTexture = "Textures/test.png";
    private String modelGeometry = "Models/test.obj";
    private float hitpoints = 10;
    private List<IControlFactory> controlFactories = new ArrayList<>();

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

    public String getModel() {
        return modelGeometry;
    }

    public float getHitpoints() {
        return hitpoints;
    }

    public String getTexture() {
        return modelTexture;
    }

    public List<IControlFactory> getControlFactories() {
        ArrayList<IControlFactory> iControlFactories = new ArrayList<>();
        iControlFactories.add(new PhysicsControlFactory());
        iControlFactories.add(new CollisionControlFactory());
        iControlFactories.add(new EntityAiControlFactory());
        return iControlFactories;
    }
}
