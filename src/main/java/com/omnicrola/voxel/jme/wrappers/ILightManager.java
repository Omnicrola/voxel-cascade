package com.omnicrola.voxel.jme.wrappers;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * Created by Eric on 2/17/2016.
 */
public interface ILightManager {
    void setSun(ColorRGBA sunColor, Vector3f sunDirection);

    void setAmbient(ColorRGBA ambientColor);
}
