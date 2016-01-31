package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.ScreenRectangle;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameInput;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by omnic on 1/24/2016.
 */
public class SelectUnitsCursorStrategy extends MoveSelectedUnitsStrategy {
    private final IGameContainer gameContainer;
    private final CursorCommandDelegator cursorCommandDelegator;
    private final LevelState levelState;
    private final WorldCursor worldCursor;
    private final JmeCursor defaultCursor;
    private boolean wasPressed;
    private Vector2f lastCursorPosition;

    public SelectUnitsCursorStrategy(IGameContainer gameContainer, CursorCommandDelegator cursorCommandDelegator, LevelState levelState, WorldCursor worldCursor, JmeCursor defaultCursor) {
        super(levelState, worldCursor, defaultCursor);
        this.gameContainer = gameContainer;
        this.cursorCommandDelegator = cursorCommandDelegator;
        this.levelState = levelState;
        this.worldCursor = worldCursor;
        this.defaultCursor = defaultCursor;
        this.lastCursorPosition = new Vector2f();
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {
        IGameInput input = this.gameContainer.input();
        if (!this.wasPressed && isPressed) {
            this.lastCursorPosition = input.getCursorPosition();
        } else if (!isPressed) {
            if (mouseHasBeenDragged()) {
                ScreenRectangle screenRectangle = new ScreenRectangle(this.lastCursorPosition, input.getCursorPosition());
                List<Spatial> spatials = this.gameContainer.world().selectAllUnitsIn(screenRectangle);
                SelectionGroup selectionGroup = new SelectionGroup(this.cursorCommandDelegator, spatials);
                this.worldCursor.setCurrentSelection(selectionGroup);
            } else {
                Optional<CollisionResult> unitUnderCursor = this.worldCursor.getUnitUnderCursor(this.levelState.getUnits());
                if (unitUnderCursor.isPresent()) {
                    Geometry unit = unitUnderCursor.get().getGeometry();
                    SelectionGroup selectionGroup = new SelectionGroup(this.cursorCommandDelegator, Arrays.asList(unit));
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
        Vector2f cursorPosition = this.gameContainer.input().getCursorPosition();
        float distance = cursorPosition.distance(this.lastCursorPosition);
        return distance > 1;
    }
}
