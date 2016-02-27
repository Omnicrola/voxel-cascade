package com.omnicrola.voxel.entities.control.jme;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by Eric on 2/27/2016.
 */
public class VoxelControlWrapper extends AbstractControl {

    private IEntityBehavior entityBehavior;

    public VoxelControlWrapper(IEntityBehavior entityBehavior) {
        this.entityBehavior = entityBehavior;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.entityBehavior.update(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
