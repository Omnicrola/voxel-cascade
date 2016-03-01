package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;

import java.util.Optional;

/**
 * Created by omnic on 1/24/2016.
 */
public class AttackCursorStrategy implements ICursorStrategy {
    private final Node empty3dCursor;
    private JmeCursor cursor2d;
    private IWorldCursor worldCursor;

    public AttackCursorStrategy(IWorldCursor worldCursor, JmeCursor cursor2d) {
        this.worldCursor = worldCursor;
        this.cursor2d = cursor2d;
        this.empty3dCursor = new Node();
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.cursor2d;
    }

    @Override
    public Spatial get3dCursor() {
        return this.empty3dCursor;
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        if (!gameMouseEvent.isPressed()) {
            attackTarget(currentSelection);
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            attackTarget(currentSelection);
        }
    }

    private void attackTarget(SelectionGroup currentSelection) {
        Optional<CollisionResult> unitUnderCursor = worldCursor.getUnitUnderCursor();
        if (unitUnderCursor.isPresent()) {
            currentSelection.orderAttackTarget(unitUnderCursor.get().getGeometry());
        } else {
            Optional<CollisionResult> terrainUnderCursor = worldCursor.getTerrainPositionUnderCursor();
            if (terrainUnderCursor.isPresent()) {
                Vector3f terrainLocation = terrainUnderCursor.get().getContactPoint();
                currentSelection.orderAttackLocation(terrainLocation);
            }
        }
        worldCursor.clearCursorStrategy();
    }
}
