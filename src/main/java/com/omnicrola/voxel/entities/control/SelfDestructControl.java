package com.omnicrola.voxel.entities.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.omnicrola.voxel.entities.EntityData;

/**
 * Created by omnic on 1/17/2016.
 */
public class SelfDestructControl extends AbstractVoxelControl {
    private float elapsedTime;
    private float lifetime;

    public SelfDestructControl(float lifetime) {
        this.lifetime = lifetime;
        this.elapsedTime = 0f;
    }

    @Override
    protected void voxelUpdate(float tpf, EntityData entityData) {
        this.elapsedTime += tpf;
        if (elapsedTime >= lifetime) {
            destroy(entityData);
        }
    }

    protected void destroy(EntityData entityData) {
        System.out.println("self-destructed");
        this.spatial.getParent().detachChild(this.spatial);
    }

    @Override
    protected void voxelRender(RenderManager rm, ViewPort vp) {

    }
}
