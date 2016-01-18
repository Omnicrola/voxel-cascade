package com.omnicrola.voxel.data.entities;

import com.omnicrola.voxel.entities.control.IControlFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/17/2016.
 */
public class ProjectileDefinition {
    private List<IControlFactory> controlFactories = new ArrayList<>();
    private String model = "Models/sphere12.obj";
    private String texture = "Textures/test.bmp";
    private float damage = 1.0f;

    public ProjectileDefinition() {
    }

    public ProjectileDefinition( String model, String texture) {
        this.controlFactories = controlFactories;
        this.model = model;
        this.texture = texture;
    }

    public List<IControlFactory> getControlFactories() {
        return controlFactories;
    }

    public String getModel() {
        return model;
    }

    public String getTexture() {
        return texture;
    }

    public float getDamage() {
        return this.damage;
    }
}
