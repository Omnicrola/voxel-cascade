package com.omnicrola.voxel.entities.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 1/17/2016.
 */
public class SelfDestructControl extends AbstractControl {
    private float elapsedTime;
    private IGamePhysics physics;
    private float lifetime;

    public SelfDestructControl(WorldManager worldManager, float lifetime) {
        this.physics = worldManager;
        this.lifetime = lifetime;
        this.elapsedTime = 0f;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.elapsedTime += tpf;
        if (elapsedTime >= lifetime) {
            destroy();
        }
    }

    protected void destroy() {
        this.physics.remove(this.spatial);
        this.spatial.getParent().detachChild(this.spatial);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
