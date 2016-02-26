package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.actions.*;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;
import com.omnicrola.voxel.terrain.IVoxelType;
import com.omnicrola.voxel.ui.CursorToken;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 1/30/2016.
 */
public class CursorCommandDelegator {
    private LevelState levelState;
    private WorldCursor worldCursor;
    private IGameInput gameInput;
    private WorldEntityBuilder worldEntityBuilder;
    private WorldManager worldManager;

    public CursorCommandDelegator(LevelState levelState,
                                  IGameInput gameInput,
                                  WorldCursor worldCursor,
                                  WorldEntityBuilder worldEntityBuilder,
                                  WorldManager worldManager) {
        this.levelState = levelState;
        this.gameInput = gameInput;
        this.worldCursor = worldCursor;
        this.worldEntityBuilder = worldEntityBuilder;
        this.worldManager = worldManager;
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
//        Spatial exampleBuildTarget = this.gameContainer.world().build().unitCursor(unitId);
//        Material validBuildMaterial = this.gameContainer.world().build().material(MaterialToken.BUILD_VALID);
//        Material invalidBuildMaterial = this.gameContainer.world().build().material(MaterialToken.BUILD_NOT_VALID);
//        BuildCursorValidityControl buildCursorValidityControl = new BuildCursorValidityControl(selectionGroup, buildRadius, validBuildMaterial, invalidBuildMaterial);
//        exampleBuildTarget.addControl(buildCursorValidityControl);
//        return exampleBuildTarget;
        return new Node();
    }

    public void setBuildVoxelStrategy(byte type) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Geometry ghostVoxel = createVoxel(new ColorRGBA(1, 1, 1, 0.5f));
        IVoxelType voxelType = this.levelState.getVoxelTypeLibrary().lookup(type);
        BuildVoxelCursorStrategy buildVoxelCursorStrategy = new BuildVoxelCursorStrategy(this.gameContainer, this.levelState, voxelType, buildCursor, ghostVoxel);
        this.worldCursor.setCursorStrategy(buildVoxelCursorStrategy);
    }

    public void setHarvestStrategy(SelectionGroup selectionGroup) {
        JmeCursor harvestCursor = getCursor(CursorToken.HARVEST);
        IWorldBuilder worldBuilder = this.gameContainer.world().build();

        Geometry cube = worldBuilder.terrainVoxel(ColorRGBA.White);
        cube.getMaterial().getAdditionalRenderState().setWireframe(true);
        cube.setLocalTranslation(0.5f, -0.5f, 0.5f);
        cube.setLocalScale(1.1f);

        HarvestCursorStrategy harvestStrategy = new HarvestCursorStrategy(this.gameContainer, this.levelState, harvestCursor, cube);
        this.worldCursor.setCursorStrategy(harvestStrategy);
    }

    private Geometry createVoxel(ColorRGBA color) {
        return this.gameContainer.world().build().terrainVoxel(color);
    }

    private JmeCursor getCursor(CursorToken cursorToken) {
        return this.gameContainer.gui().build().cursor(cursorToken);
    }

}
