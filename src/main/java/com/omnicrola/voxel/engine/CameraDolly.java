package com.omnicrola.voxel.engine;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * Created by Eric on 2/26/2016.
 */
public class CameraDolly {
    private static final float CAMERA_MOVE_SPEED = 10f;

    Camera camera;

    public CameraDolly(Camera camera) {
        this.camera = camera;
    }

    public void moveCamera(float amount, boolean sideways) {
        Vector3f location;
        if (sideways) {
            location = this.camera.getLocation().add(amount * CAMERA_MOVE_SPEED, 0, 0);
        } else {
            location = this.camera.getLocation().add(0, 0, amount * CAMERA_MOVE_SPEED);
        }

        this.camera.setLocation(location);
    }

}
