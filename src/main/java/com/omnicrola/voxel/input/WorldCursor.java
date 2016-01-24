package com.omnicrola.voxel.input;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.physics.CollisionDistanceComparator;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.Optional;

/**
 * Created by omnic on 1/16/2016.
 */
public class WorldCursor extends Node {
    private final InputManager inputManager;
    private final Camera camera;
    private final CollisionDistanceComparator collisionDistanceComparator;
    private Node terrainNode;

    public WorldCursor(InputManager inputManager, Camera camera, Node terrainNode) {
        this.inputManager = inputManager;
        this.camera = camera;
        this.terrainNode = terrainNode;
        this.collisionDistanceComparator = new CollisionDistanceComparator();
    }

    @Override
    public void updateLogicalState(float tpf) {
        Ray pickRay = getPickRay();
        setPositionToNearestVoxel(pickRay);
    }

    private void setPositionToNearestVoxel(Ray pickRay) {
        CollisionResults results = new CollisionResults();
        this.terrainNode.collideWith(pickRay, results);
        if (results.size() > 0) {
            Vector3f contactPoint = results.getClosestCollision().getContactPoint();
            this.setLocalTranslation(contactPoint);
        }
    }

    private Ray getPickRay() {
        Vector2f cursorPosition = this.inputManager.getCursorPosition();
        Vector3f cursor3d = this.camera.getWorldCoordinates(cursorPosition.clone(), 0f).clone();
        Vector3f direction = this.camera.getWorldCoordinates(cursorPosition.clone(), 1f).subtractLocal(cursor3d)
                .normalizeLocal();
        return new Ray(cursor3d, direction);
    }

    public void setVisible(boolean visible) {
        if (visible) {
            this.setCullHint(CullHint.Inherit);
        } else {
            this.setCullHint(CullHint.Always);
        }
    }

    public Optional<CollisionResult> getUnitUnderCursor(Node targetNode) {
        CollisionResults results = new CollisionResults();
        Ray pickRay = getPickRay();
        targetNode.collideWith(pickRay, results);
        return VoxelUtil.convertToStream(results)
                .filter(c -> isSelectableUnit(c))
                .sorted(this.collisionDistanceComparator)
                .findFirst();
    }

    private boolean isSelectableUnit(CollisionResult collision) {
        Geometry geometry = collision.getGeometry();
        boolean isUnit = VoxelUtil.booleanData(geometry, EntityDataKeys.IS_UNIT);
        boolean isSelectable = VoxelUtil.booleanData(geometry, EntityDataKeys.IS_SELECTABLE);
        return isUnit && isSelectable;
    }

    public Optional<CollisionResult> getTerrainUnderCursor(Node terrain) {
        CollisionResults results = new CollisionResults();
        Ray pickRay = getPickRay();
        terrain.collideWith(pickRay, results);
        return VoxelUtil.convertToStream(results)
                .sorted(this.collisionDistanceComparator)
                .findFirst();
    }
}
