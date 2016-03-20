package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.InputManager;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.input.*;
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
    private final CursorCommandAdaptor cursorCommandAdaptor;
    private final IWorldCursor worldCursor;
    private boolean wasPressed;
    private Vector2f lastCursorPosition;
    private InputManager inputManager;

    public SelectUnitsCursorStrategy(CursorCommandAdaptor cursorCommandAdaptor,
                                     IWorldCursor worldCursor,
                                     InputManager inputManager,
                                     JmeCursor defaultCursor,
                                     WorldCommandProcessor worldCommandProcessor) {
        super(worldCursor, defaultCursor, worldCommandProcessor);
        this.cursorCommandAdaptor = cursorCommandAdaptor;
        this.worldCursor = worldCursor;
        this.inputManager = inputManager;
        this.lastCursorPosition = new Vector2f();
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        boolean isNewMousePress = !this.wasPressed && gameMouseEvent.isPressed();
        if (isNewMousePress) {
            this.lastCursorPosition = this.inputManager.getCursorPosition().clone();
        } else {
            boolean mouseWasJustReleased = !gameMouseEvent.isPressed();
            if (mouseWasJustReleased) {
                if (mouseHasBeenDragged()) {
                    ScreenRectangle screenRectangle = new ScreenRectangle(this.lastCursorPosition, this.inputManager.getCursorPosition());
                    List<Spatial> spatials = selectUnitsOrBuilding(screenRectangle);
                    SelectionGroup selectionGroup = selectUnits(spatials);
                    this.worldCursor.setCurrentSelection(selectionGroup);
                } else {
                    Optional<CollisionResult> unitUnderCursor = this.worldCursor.getUnitUnderCursor();
                    if (unitUnderCursor.isPresent()) {
                        Geometry unit = unitUnderCursor.get().getGeometry();
                        SelectionGroup selectionGroup = selectUnits(Arrays.asList(unit));
                        this.worldCursor.setCurrentSelection(selectionGroup);
                    }
                }
            }
        }
        this.wasPressed = gameMouseEvent.isPressed();
    }

    private SelectionGroup selectUnits(List<Spatial> spatials) {
        return new SelectionGroup(this.cursorCommandAdaptor, spatials);
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        if (!isPressed) {
            this.moveToLocation(currentSelection);
        }
    }

    private List<Spatial> selectUnitsOrBuilding(ScreenRectangle screenRectangle) {
        List<Spatial> spatials = this.worldCursor.selectAllUnitsIn(screenRectangle);
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
        Vector2f cursorPosition = this.inputManager.getCursorPosition();
        float distance = cursorPosition.distance(this.lastCursorPosition);
        return distance > 1;
    }
}
