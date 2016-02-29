package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.actions.*;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.ui.CursorToken;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by omnic on 1/30/2016.
 */
public class CursorCommandDelegator {
    private LevelState levelState;
    private Cursor2dProvider cursor2dProvider;
    private WorldCursor worldCursor;
    private IGameInput gameInput;
    private WorldEntityBuilder worldEntityBuilder;
    private WorldManager worldManager;
    private ITerrainManager terrainManager;

    public CursorCommandDelegator(LevelState levelState,
                                  IGameInput gameInput,
                                  Cursor2dProvider cursor2dProvider,
                                  WorldCursor worldCursor,
                                  WorldEntityBuilder worldEntityBuilder,
                                  WorldManager worldManager,
                                  ITerrainManager terrainManager) {
        this.levelState = levelState;
        this.gameInput = gameInput;
        this.cursor2dProvider = cursor2dProvider;
        this.worldCursor = worldCursor;
        this.worldEntityBuilder = worldEntityBuilder;
        this.worldManager = worldManager;
        this.terrainManager = terrainManager;
    }

    public SelectUnitsCursorStrategy setSelectStrategy() {
        JmeCursor defaultCursor = getCursor(CursorToken.DEFAULT);
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = new SelectUnitsCursorStrategy(
                this, levelState, worldCursor, this.gameInput, defaultCursor);
        this.worldCursor.setCursorStrategy(selectUnitsCursorStrategy);
        return selectUnitsCursorStrategy;
    }

    public void setAttackStrategy() {
        JmeCursor attackCursor = getCursor(CursorToken.ATTACK);
        AttackCursorStrategy attackCursorStrategy = new AttackCursorStrategy(this.levelState, attackCursor);
        this.worldCursor.setCursorStrategy(attackCursorStrategy);
    }

    public void setMoveStrategy() {
        JmeCursor moveCursor = getCursor(CursorToken.MOVE);
        MoveSelectedUnitsStrategy moveSelectedUnitsStrategy = new MoveSelectedUnitsStrategy(this.levelState, this.worldCursor, moveCursor);
        this.worldCursor.setCursorStrategy(moveSelectedUnitsStrategy);
    }

    public void setBuildStrategy(SelectionGroup selectionGroup) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        EmptyBuildStrategy emptyBuildStrategy = new EmptyBuildStrategy(buildCursor);
        this.worldCursor.setCursorStrategy(emptyBuildStrategy);
    }

    public void setBuildUnitStrategy(int unitId, float buildRadius, SelectionGroup selectionGroup) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Spatial exampleBuildTarget = createValidityCheckingModel(unitId, buildRadius, selectionGroup);

        BuildUnitStrategy buildUnitStrategy = new BuildUnitStrategy(
                this.levelState,
                unitId,
                buildCursor,
                exampleBuildTarget,
                worldEntityBuilder,
                worldManager);
        this.worldCursor.setCursorStrategy(buildUnitStrategy);
    }

    private Spatial createValidityCheckingModel(int unitId, float buildRadius, SelectionGroup selectionGroup) {
        Spatial placeholder = this.worldEntityBuilder.buildPlaceholderUnit(unitId, buildRadius, selectionGroup);
        return placeholder;
    }

    public void setBuildVoxelStrategy(byte type) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Geometry placeholderVoxel = this.worldEntityBuilder.buildCube(ColorRGBA.Green);
        BuildVoxelCursorStrategy buildVoxelCursorStrategy = new BuildVoxelCursorStrategy(
                this.levelState,
                type,
                buildCursor,
                this.terrainManager,
                this.worldManager,
                placeholderVoxel);
        this.worldCursor.setCursorStrategy(buildVoxelCursorStrategy);
    }

    public void setHarvestStrategy() {
        JmeCursor harvestCursor = getCursor(CursorToken.HARVEST);

        Geometry cube = this.worldEntityBuilder.buildCube(ColorRGBA.White);
        cube.getMaterial().getAdditionalRenderState().setWireframe(true);
        cube.setLocalTranslation(0.5f, -0.5f, 0.5f);
        cube.setLocalScale(1.1f);

        HarvestCursorStrategy harvestStrategy = new HarvestCursorStrategy(this.terrainManager, this.levelState, harvestCursor, cube);
        this.worldCursor.setCursorStrategy(harvestStrategy);
    }

    private JmeCursor getCursor(CursorToken cursorToken) {
        return this.cursor2dProvider.getCursor(cursorToken);
    }

}
