package com.omnicrola.voxel.entities.control.unit;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.physics.BetterCapsuleCollisionShape;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Optional;

/**
 * Created by omnic on 1/23/2016.
 */
public class GroundVehicleControl extends CharacterControl   {

    private final ITerrainManager terrainManager;
    private final float mass;
    private Geometry geometry;

    private static CollisionShape getShape() {
        BetterCapsuleCollisionShape capsuleCollisionShape = new BetterCapsuleCollisionShape(0.25f, 0.25f);
        CompoundCollisionShape compoundCollisionShape = new CompoundCollisionShape();
        compoundCollisionShape.addChildShape(capsuleCollisionShape, new Vector3f(0, -0.25f, 0));
        return capsuleCollisionShape;
    }


    public GroundVehicleControl(ITerrainManager terrainManager, float mass) {
        super(getShape(), 1);
        this.terrainManager = terrainManager;
        this.mass = mass;
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
        boolean isBelowTerrain = this.terrainManager.isBelowTerrain(this.geometry);
        if (isBelowTerrain) {
            Optional<VoxelData> lowestEmptyVoxel = this.terrainManager.findLowestEmptyVoxel(physicsLocation);
            if (lowestEmptyVoxel.isPresent()) {
                Vector3f newPosition = lowestEmptyVoxel.get().getLocation();
                this.warp(newPosition);
            }
        }
    }
}
