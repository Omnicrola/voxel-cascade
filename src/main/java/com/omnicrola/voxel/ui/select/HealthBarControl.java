package com.omnicrola.voxel.ui.select;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * Created by omnic on 3/17/2016.
 */
public class HealthBarControl extends AbstractControl {
    private HealthBar healthBar;

    HealthBarControl(HealthBar hb) {
        this.healthBar = hb;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.healthBar.setPercent(1.0f);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
