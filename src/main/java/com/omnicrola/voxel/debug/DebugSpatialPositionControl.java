package com.omnicrola.voxel.debug;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 3/12/2016.
 */
public class DebugSpatialPositionControl extends AbstractControl {
    @Override
    protected void controlUpdate(float tpf) {
        System.out.println(this.spatial.getName() + " " + this.spatial.getWorldTranslation());
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
