package com.omnicrola.voxel.engine;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.jme.wrappers.ILightManager;

/**
 * Created by Eric on 2/17/2016.
 */
public class LightManager implements ILightManager {
    private final DirectionalLight sun;
    private DirectionalLight rimLight;
    private final AmbientLight ambientLight;

    public LightManager(DirectionalLight sun, DirectionalLight rimLight, AmbientLight ambientLight) {
        this.sun = sun;
        this.rimLight = rimLight;
        this.ambientLight = ambientLight;
    }

    @Override
    public void setSun(ColorRGBA sunColor, Vector3f sunDirection) {
        this.sun.setColor(sunColor);
        this.sun.setDirection(sunDirection);
        this.rimLight.setDirection(sunDirection.mult(-1f));
    }

    @Override
    public void setAmbient(ColorRGBA ambientColor) {
        this.ambientLight.setColor(ambientColor);
    }

    public DirectionalLight getSun() {
        return this.sun;
    }
}
