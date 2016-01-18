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
        nodeA.getControl(CollisionController.class).collideWith(nodeB);
        nodeB.getControl(CollisionController.class).collideWith(nodeA);
    }
}
