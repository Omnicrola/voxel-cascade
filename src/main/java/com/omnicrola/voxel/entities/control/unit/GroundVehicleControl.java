package com.omnicrola.voxel.entities.control.unit;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.terrain.ITerrainManager;

/**
 * Created by omnic on 1/23/2016.
 */
public class GroundVehicleControl extends AbstractControl {

    private final ITerrainManager terrainManager;
    private Geometry geometry;

    public GroundVehicleControl(ITerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.geometry = (Geometry) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
