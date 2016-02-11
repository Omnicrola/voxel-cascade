package com.omnicrola.voxel.physics;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/23/2016.
 */
public class GroundVehicleControl extends CharacterControl {

    private final IGameContainer gameContainer;
    private Geometry geometry;

    private static CollisionShape getShape() {
        BetterCapsuleCollisionShape capsuleCollisionShape = new BetterCapsuleCollisionShape(0.25f, 0.25f);
        CompoundCollisionShape compoundCollisionShape = new CompoundCollisionShape();
        compoundCollisionShape.addChildShape(capsuleCollisionShape, new Vector3f(0, -0.25f, 0));
        return capsuleCollisionShape;
    }


    public GroundVehicleControl(IGameContainer gameContainer, float mass) {
        super(getShape(), 1);
        this.gameContainer = gameContainer;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.geometry = (Geometry) spatial;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        Vector3f physicsLocation = this.getPhysicsLocation();
        boolean isBelowTerrain = this.gameContainer.world().isBelowTerrain(this.geometry);
        if (isBelowTerrain) {
            Vector3f newPosition = this.gameContainer.world().getSpawnPointFor(physicsLocation).add(0, 0.1f, 0);
            this.warp(newPosition);
        }
    }
}
