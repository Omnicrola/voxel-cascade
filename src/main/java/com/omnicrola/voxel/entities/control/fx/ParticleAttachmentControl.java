package com.omnicrola.voxel.entities.control.fx;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by omnic on 3/13/2016.
 */
public class ParticleAttachmentControl extends AbstractControl {
    private final Effect effect;
    private final EntityControlAdapter entityControlAdapter;
    private final Vector3f offset;

    public ParticleAttachmentControl(Effect effect, EntityControlAdapter entityControlAdapter, Vector3f offset) {
        this.effect = effect;
        this.entityControlAdapter = entityControlAdapter;
        this.offset = offset;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (VoxelUtil.isAlive(this.spatial)) {
            this.effect.setLocation(this.spatial.getWorldTranslation().add(offset));
        } else {
            this.entityControlAdapter.getWorldManager().removeSpatial(this.effect.getSpatial());
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
