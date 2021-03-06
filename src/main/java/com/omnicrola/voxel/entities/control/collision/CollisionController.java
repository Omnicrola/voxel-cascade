package com.omnicrola.voxel.entities.control.collision;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 1/17/2016.
 */
public class CollisionController extends AbstractControl {

    private ISpatialCollisionHandler spatialCollisionHandler;

    public CollisionController(ISpatialCollisionHandler spatialCollisionHandler) {
        this.spatialCollisionHandler = spatialCollisionHandler;
    }

    @Override
    protected void controlUpdate(float tpf) {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void collideWith(Spatial otherObject) {
        spatialCollisionHandler.collideWith(otherObject);
    }
}
