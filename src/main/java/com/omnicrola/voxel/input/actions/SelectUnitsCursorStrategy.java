package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.ScreenRectangle;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 1/24/2016.
 */
public class SelectUnitsCursorStrategy extends MoveSelectedUnitsStrategy {
    private boolean wasPressed;
    private Vector2f lastCursorPosition;
    private IGameInput input;
    private IGameWorld gameWorld;

    public SelectUnitsCursorStrategy(IGameInput inputManager,
                                     IGameWorld gameWorld,
                                     LevelState levelState,
                                     WorldCursor worldCursor, JmeCursor cursor2d) {
        super(levelState, worldCursor, cursor2d);
        this.input = inputManager;
        this.gameWorld = gameWorld;
        this.lastCursorPosition = new Vector2f();
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {
        if (!this.wasPressed && isPressed) {
            this.lastCursorPosition = this.input.getCursorPosition();
        } else if (!isPressed) {
            if (mouseHasBeenDragged()) {
                ScreenRectangle screenRectangle = new ScreenRectangle(this.lastCursorPosition, this.input.getCursorPosition());
                List<Spatial> spatials = this.gameWorld.selectAllUnitsIn(screenRectangle);
                SelectionGroup selectionGroup = new SelectionGroup(spatials);
                this.worldCursor.setCurrentSelection(selectionGroup);
            } else {
                Optional<CollisionResult> unitUnderCursor = this.worldCursor.getUnitUnderCursor(this.levelState.getUnits());
                if (unitUnderCursor.isPresent()) {
                    Geometry unit = unitUnderCursor.get().getGeometry();
                    SelectionGroup selectionGroup = new SelectionGroup(Arrays.asList(unit));
                    this.worldCursor.setCurrentSelection(selectionGroup);
                }
            }
        }
        this.wasPressed = isPressed;
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            this.moveToLocation(currentSelection);
        }
    }

    private boolean mouseHasBeenDragged() {
        Vector2f cursorPosition = this.input.getCursorPosition();
        float distance = cursorPosition.distance(this.lastCursorPosition);
        return distance > 1;
    }
}
