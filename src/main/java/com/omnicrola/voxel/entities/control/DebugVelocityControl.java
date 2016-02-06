package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.physics.GroundVehicleControl;

/**
 * Created by omnic on 2/6/2016.
 */
public class DebugVelocityControl extends AbstractControl {
    private Spatial target;

    public DebugVelocityControl(Spatial target) {
        this.target = target;
    }

    @Override
    protected void controlUpdate(float tpf) {
        GroundVehicleControl vehicleControl = this.target.getControl(GroundVehicleControl.class);
        if (vehicleControl != null) {
            Vector3f walkDirection = vehicleControl.getLinearVelocity();

            Vector3f lookTarget = walkDirection.add(this.spatial.getWorldTranslation());
            this.spatial.lookAt(lookTarget, Vector3f.UNIT_Y);
            this.spatial.setLocalTranslation(this.target.getWorldTranslation().add(0, 0.1f, 0));
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
