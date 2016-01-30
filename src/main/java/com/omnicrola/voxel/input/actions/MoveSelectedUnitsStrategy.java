package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
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
    private JmeCursor cursor2d;

    public MoveSelectedUnitsStrategy(LevelState currentLevelState,
                                     WorldCursor worldCursor,
                                     JmeCursor cursor2d) {
        this.levelState = currentLevelState;
        this.worldCursor = worldCursor;
        this.cursor2d = cursor2d;
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.cursor2d;
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            moveToLocation(currentSelection);
            this.worldCursor.clearCursorStrategy();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            moveToLocation(currentSelection);
            this.worldCursor.clearCursorStrategy();
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
