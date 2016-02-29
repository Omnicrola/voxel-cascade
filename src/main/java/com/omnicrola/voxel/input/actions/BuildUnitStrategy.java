package com.omnicrola.voxel.input.actions;

import com.jme3.collision.CollisionResult;
import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.entities.control.construction.BuildCursorValidityControl;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

import java.util.Optional;

/**
 * Created by Eric on 1/30/2016.
 */
public class BuildUnitStrategy implements ICursorStrategy {

    private final LevelState levelState;
    private final int unitId;
    private final JmeCursor buildCursor;
    private Spatial targetModel;
    private WorldEntityBuilder worldEntityBuilder;
    private WorldManager worldManager;

    public BuildUnitStrategy(LevelState levelState,
                             int unitId,
                             JmeCursor buildCursor,
                             Spatial targetModel,
                             WorldEntityBuilder worldEntityBuilder,
                             WorldManager worldManager) {
        this.levelState = levelState;
        this.unitId = unitId;
        this.buildCursor = buildCursor;
        this.targetModel = targetModel;
        this.worldEntityBuilder = worldEntityBuilder;
        this.worldManager = worldManager;
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        if (!gameMouseEvent.isPressed()) {
            if (isValidBuildLocation()) {
                WorldCursor worldCursor = this.levelState.getWorldCursor();
                Optional<CollisionResult> terrainUnderCursor = worldCursor.getTerrainPositionUnderCursor();
                if (terrainUnderCursor.isPresent()) {
                    Vector3f location = terrainUnderCursor.get().getContactPoint();
                    UnitPlacement unitPlacement = new UnitPlacement(this.unitId, levelState.getPlayerTeam().getId(), location);
                    Unit newUnit = this.worldEntityBuilder.buildUnit(unitPlacement);
                    this.worldManager.addUnit(newUnit);

                    worldCursor.clearCursorStrategy();
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
