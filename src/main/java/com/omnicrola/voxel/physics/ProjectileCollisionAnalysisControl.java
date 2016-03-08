package com.omnicrola.voxel.physics;

import com.jme3.collision.CollisionResults;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.control.collision.CollisionController;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;
import com.omnicrola.voxel.world.WorldRootNode;

/**
 * Created by Eric on 3/7/2016.
 */
public class ProjectileCollisionAnalysisControl extends AbstractControl {
    private Node parentNode;
    private WorldRootNode worldRootNode;

    public ProjectileCollisionAnalysisControl(WorldRootNode worldRootNode) {
        this.worldRootNode = worldRootNode;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.parentNode = (Node) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.parentNode.getChildren().forEach(s -> collide(s));
    }


    private void collide(Spatial spatial) {
        CollisionResults results = new CollisionResults();
        this.worldRootNode.getUnitsNode().collideWith(spatial.getWorldBound(), results);
        results.forEach(c -> handleCollision(spatial, c.getGeometry()));
    }

    private void handleCollision(Spatial nodeA, Spatial nodeB) {
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

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
