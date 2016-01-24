package com.omnicrola.voxel.physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/23/2016.
 */
public class GroundVehicleControl extends CharacterControl {

    public GroundVehicleControl(float mass) {
        super(getShape(), 1);
    }

    private static BoxCollisionShape getShape() {
        return new BoxCollisionShape(new Vector3f(0.5f, 0.25f, 0.5f));
    }

}
