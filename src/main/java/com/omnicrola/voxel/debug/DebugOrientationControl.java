package com.omnicrola.voxel.debug;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 2/6/2016.
 */
public class DebugOrientationControl extends AbstractControl {
    private Spatial target;

    public DebugOrientationControl(Spatial target) {
        this.target = target;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.spatial.setLocalTranslation(this.target.getWorldTranslation());
        this.spatial.setLocalRotation(this.target.getWorldRotation());
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
