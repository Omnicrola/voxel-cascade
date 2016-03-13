package com.omnicrola.voxel.entities.control.move;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 3/13/2016.
 */
public class RotationControl extends AbstractControl {
    private final Vector3f rotationAxis;
    private final float rotationAmount;
    private float rotation;

    public RotationControl(Vector3f rotationAxis, float rotationAmount) {
        this.rotationAxis = rotationAxis;
        this.rotationAmount = rotationAmount;
        this.rotation = 0;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.rotation += this.rotationAmount * tpf;
        Quaternion quaternion = new Quaternion();
        quaternion.fromAngleAxis(this.rotation, this.rotationAxis);
        this.spatial.setLocalRotation(quaternion);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
