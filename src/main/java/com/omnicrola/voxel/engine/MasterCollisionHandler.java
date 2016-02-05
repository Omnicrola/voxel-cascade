package com.omnicrola.voxel.engine;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.physics.CollisionController;

/**
 * Created by omnic on 1/17/2016.
 */
public class MasterCollisionHandler implements PhysicsCollisionListener {

    @Override
    public void collision(PhysicsCollisionEvent event) {
        Spatial nodeA = event.getNodeA();
        Spatial nodeB = event.getNodeB();
        collideNodes(nodeA, nodeB);
        collideNodes(nodeB, nodeA);
    }

    private void collideNodes(Spatial primary, Spatial secondary) {
        CollisionController collisionControl = primary.getControl(CollisionController.class);
        if (collisionControl != null) {
            collisionControl.collideWith(secondary);
        }
    }
}
