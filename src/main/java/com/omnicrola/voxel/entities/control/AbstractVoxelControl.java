package com.omnicrola.voxel.entities.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.EntityData;
import com.omnicrola.voxel.settings.VoxelGlobals;

/**
 * Created by omnic on 1/17/2016.
 */
public abstract class AbstractVoxelControl extends AbstractControl {

    @Override
    final protected void controlUpdate(float tpf) {
        EntityData entityData = this.spatial.getUserData(VoxelGlobals.ENTITY_DATA);
        if (entityData == null) {
            throw new RuntimeException("This control is attached to a JME spatial without EntityData");
        }
        voxelUpdate(tpf, entityData);
    }

    protected abstract void voxelUpdate(float tpf, EntityData entityData);

    @Override
    final protected void controlRender(RenderManager rm, ViewPort vp) {
        voxelRender(rm, vp);
    }

    protected abstract void voxelRender(RenderManager rm, ViewPort vp);
}
