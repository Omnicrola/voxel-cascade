package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.util.VoxelUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        IGameInput input = this.gameContainer.input();
        if (!this.wasPressed && gameMouseEvent.isPressed()) {
            this.lastCursorPosition = input.getCursorPosition();
        } else if (!gameMouseEvent.isPressed()) {
            if (mouseHasBeenDragged()) {
                ScreenRectangle screenRectangle = new ScreenRectangle(this.lastCursorPosition, input.getCursorPosition());
                List<Spatial> spatials = selectUnitsOrBuilding(screenRectangle);
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
        this.wasPressed = gameMouseEvent.isPressed();
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            this.moveToLocation(currentSelection);
        }
    }

    private List<Spatial> selectUnitsOrBuilding(ScreenRectangle screenRectangle) {
        List<Spatial> spatials = this.gameContainer.world().selectAllUnitsIn(screenRectangle);
        List<Spatial> structures = findStructures(spatials);
        if (structures.size() > 0) {
            return structures;
        } else {
            List<Spatial> units = findUnits(spatials);
            return units;
        }
    }

    private List<Spatial> findUnits(List<Spatial> spatials) {
        return spatials.stream()
                .filter(s -> VoxelUtil.booleanData(s, EntityDataKeys.IS_UNIT))
                .collect(Collectors.toList());
    }

    private List<Spatial> findStructures(List<Spatial> spatials) {
        return spatials.stream()
                .filter(s -> VoxelUtil.booleanData(s, EntityDataKeys.IS_STRUCTURE))
                .collect(Collectors.toList());
    }

    private boolean mouseHasBeenDragged() {
        Vector2f cursorPosition = this.gameContainer.input().getCursorPosition();
        float distance = cursorPosition.distance(this.lastCursorPosition);
        return distance > 1;
    }
}
