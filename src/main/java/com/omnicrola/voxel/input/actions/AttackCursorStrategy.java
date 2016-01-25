package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;

import java.util.Optional;

/**
 * Created by omnic on 1/24/2016.
 */
public class AttackCursorStrategy implements ICursorStrategy {
    private LevelState currentLevelState;

    public AttackCursorStrategy(LevelState currentLevelState) {
        this.currentLevelState = currentLevelState;
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
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
        WorldCursor worldCursor = this.currentLevelState.getWorldCursor();
        Optional<CollisionResult> unitUnderCursor = worldCursor.getUnitUnderCursor(this.currentLevelState.getUnits());
        if (unitUnderCursor.isPresent()) {
            currentSelection.orderAttackTarget(unitUnderCursor.get().getGeometry());
        } else {
            Optional<CollisionResult> terrainUnderCursor = worldCursor.getTerrainUnderCursor(this.currentLevelState.getTerrain());
            if (terrainUnderCursor.isPresent()) {
                Vector3f terrainLocation = terrainUnderCursor.get().getContactNormal();
                currentSelection.orderAttackLocation(terrainLocation);
            }
        }
    }
}
