package com.omnicrola.voxel.engine;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.collision.CollisionController;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by omnic on 1/17/2016.
 */
public class MasterCollisionHandler implements PhysicsCollisionListener {

    @Override
    public void collision(PhysicsCollisionEvent event) {
        Spatial nodeA = event.getNodeA();
        Spatial nodeB = event.getNodeB();
        if (nodesAreCollidable(nodeA, nodeB)) {
            collideNodes(nodeA, nodeB);
            collideNodes(nodeB, nodeA);
        }
    }

    private boolean nodesAreCollidable(Spatial nodeA, Spatial nodeB) {
        boolean isCollidableA = VoxelUtil.booleanData(nodeA, EntityDataKeys.IS_COLLIDABLE);
        boolean isCollidableB = VoxelUtil.booleanData(nodeB, EntityDataKeys.IS_COLLIDABLE);
        return isCollidableA && isCollidableB;
    }

    private void collideNodes(Spatial primary, Spatial secondary) {
        CollisionController collisionControl = primary.getControl(CollisionController.class);
        if (collisionControl != null) {
            collisionControl.collideWith(secondary);
        }
    }
}
