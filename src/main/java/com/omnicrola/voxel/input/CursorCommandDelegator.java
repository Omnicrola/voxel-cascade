package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.entities.control.BuildCursorValidityControl;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.input.actions.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;
import com.omnicrola.voxel.ui.CursorToken;

/**
 * Created by omnic on 1/30/2016.
 */
public class CursorCommandDelegator {
    private IGameContainer gameContainer;
    private LevelState levelState;
    private WorldCursor worldCursor;

    public CursorCommandDelegator(IGameContainer gameContainer, LevelState levelState, WorldCursor worldCursor) {
        this.gameContainer = gameContainer;
        this.levelState = levelState;
        this.worldCursor = worldCursor;
    }

    public SelectUnitsCursorStrategy setSelectStrategy() {
        JmeCursor defaultCursor = getCursor(CursorToken.DEFAULT);
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = new SelectUnitsCursorStrategy(this.gameContainer,
                this, levelState, worldCursor, defaultCursor);
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
                this.gameContainer,
                this.levelState,
                unitId,
                buildCursor,
                exampleBuildTarget);
        this.worldCursor.setCursorStrategy(buildUnitStrategy);
    }

    private Spatial createValidityCheckingModel(int unitId, float buildRadius, SelectionGroup selectionGroup) {
        Spatial exampleBuildTarget = this.gameContainer.world().build().unitCursor(unitId);
        Material validBuildMaterial = this.gameContainer.world().build().material(MaterialToken.BUILD_VALID);
        Material invalidBuildMaterial = this.gameContainer.world().build().material(MaterialToken.BUILD_NOT_VALID);
        BuildCursorValidityControl buildCursorValidityControl = new BuildCursorValidityControl(selectionGroup, buildRadius, validBuildMaterial, invalidBuildMaterial);
        exampleBuildTarget.addControl(buildCursorValidityControl);
        return exampleBuildTarget;
    }

    public void setBuildVoxelStrategy(byte voxelType) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Geometry ghostVoxel = createVoxel(new ColorRGBA(1, 1, 1, 0.5f));
        BuildVoxelStrategy buildVoxelStrategy = new BuildVoxelStrategy(this.gameContainer, this.levelState, voxelType, buildCursor, ghostVoxel);
        this.worldCursor.setCursorStrategy(buildVoxelStrategy);
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
