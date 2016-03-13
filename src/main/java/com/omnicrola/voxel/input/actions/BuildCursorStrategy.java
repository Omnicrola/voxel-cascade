package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.construction.BuildCursorValidityControl;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.SelectionGroup;

import java.util.Optional;

/**
 * Created by Eric on 1/30/2016.
 */
public class BuildCursorStrategy implements ICursorStrategy {

    private final JmeCursor buildCursor;
    private Spatial targetModel;
    private IBuildCompletionStrategy buildStrategy;
    private IWorldCursor worldCursor;

    public BuildCursorStrategy(JmeCursor buildCursor,
                               Spatial targetModel,
                               IWorldCursor worldCursor,
                               IBuildCompletionStrategy buildStrategy) {
        this.buildCursor = buildCursor;
        this.targetModel = targetModel;
        this.worldCursor = worldCursor;
        this.buildStrategy = buildStrategy;
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        if (!gameMouseEvent.isPressed()) {
            if (isValidBuildLocation()) {
                Optional<CollisionResult> terrainUnderCursor = worldCursor.getTerrainPositionUnderCursor();
                if (terrainUnderCursor.isPresent()) {
                    if (this.buildStrategy.isAbleToBuild()) {
                        CollisionResult collisionResult = terrainUnderCursor.get();
                        Vector3f location = collisionResult.getContactPoint();
                        this.buildStrategy.build(location);
                        worldCursor.clearCursorStrategy();
                    }
                }
            }
        }
    }

    private boolean isValidBuildLocation() {
        BuildCursorValidityControl validityControl = this.targetModel.getControl(BuildCursorValidityControl.class);
        return validityControl.isValidLocation();
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.buildCursor;
    }

    @Override
    public Spatial get3dCursor() {
        return this.targetModel;
    }
}
