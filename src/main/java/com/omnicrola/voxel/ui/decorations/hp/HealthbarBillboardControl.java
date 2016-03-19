package com.omnicrola.voxel.ui.decorations.hp;

import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.BillboardControl;

/**
 * Created by Eric on 3/19/2016.
 */
public class HealthbarBillboardControl extends BillboardControl {

    public static final float DISTANCE_SCALE_ADJUSTMENT = 0.09f;

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        scaleAccordingToCameraDistance(vp);
        // call super last to allow parent to fix geometric state,
        // which is normally only updated in the controlUpdate() method
        super.controlRender(rm, vp);
    }

    private void scaleAccordingToCameraDistance(ViewPort vp) {
        Camera camera = vp.getCamera();
        float distance = camera.getLocation().distance(this.spatial.getWorldTranslation());
        float scale = distance * DISTANCE_SCALE_ADJUSTMENT;
        this.spatial.setLocalScale(scale);
    }
}
