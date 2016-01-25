package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;

import java.util.Optional;

/**
 * Created by omnic on 1/24/2016.
 */
public class MoveSelectedUnitsStrategy implements ICursorStrategy {

    protected LevelState levelState;
    protected WorldCursor worldCursor;

    public MoveSelectedUnitsStrategy(LevelState currentLevelState, WorldCursor worldCursor) {
        this.levelState = currentLevelState;
        this.worldCursor = worldCursor;
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            moveToLocation(currentSelection);
            this.worldCursor.clearSelection();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            moveToLocation(currentSelection);
        }
    }

    protected void moveToLocation(SelectionGroup currentSelection) {
        Node terrain = levelState.getTerrain();
        Optional<CollisionResult> terrainUnderCursor = this.worldCursor.getTerrainUnderCursor(terrain);
        if (terrainUnderCursor.isPresent()) {
            Vector3f location = terrainUnderCursor.get().getContactPoint();
            currentSelection.orderMoveToLocation(location);
        }
    }
}
