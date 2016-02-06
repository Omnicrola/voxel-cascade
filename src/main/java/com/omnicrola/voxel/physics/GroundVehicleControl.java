package com.omnicrola.voxel.physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/23/2016.
 */
public class GroundVehicleControl extends CharacterControl {

    private Geometry geometry;

    private static BoxCollisionShape getShape() {
        return new BoxCollisionShape(new Vector3f(0.5f, 0.25f, 0.5f));
    }

    private IGameWorld gameWorld;

    public GroundVehicleControl(IGameWorld gameWorld, float mass) {
        super(getShape(), 0.5f);
        this.gameWorld = gameWorld;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.geometry = (Geometry) spatial;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (this.gameWorld.isBelowTerrain(this.geometry)) {
            this.warp(this.getPhysicsLocation().add(0, 1, 0));
        }
    }
}
