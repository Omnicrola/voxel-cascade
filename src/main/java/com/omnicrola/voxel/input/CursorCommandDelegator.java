package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.input.actions.*;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
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

    public void setBuildUnitStrategy(int unitId) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Spatial exampleBuildTarget = this.gameContainer.world().build().unit(unitId, TeamData.NEUTRAL);
        Material material = this.gameContainer.world().build().material(MaterialToken.GHOST_BUILDING);
        exampleBuildTarget.setMaterial(material);
        BuildUnitStrategy buildUnitStrategy = new BuildUnitStrategy(this.gameContainer, this.levelState, unitId, buildCursor, exampleBuildTarget);
        this.worldCursor.setCursorStrategy(buildUnitStrategy);
    }

    public void setBuildVoxelStrategy(byte voxelType) {
        JmeCursor buildCursor = getCursor(CursorToken.BUILD);
        Geometry ghostVoxel = createVoxel(new ColorRGBA(1, 1, 1, 0.5f));
        BuildVoxelStrategy buildVoxelStrategy = new BuildVoxelStrategy(this.gameContainer, this.levelState, voxelType, buildCursor, ghostVoxel);
        this.worldCursor.setCursorStrategy(buildVoxelStrategy);
    }

    private Geometry createVoxel(ColorRGBA color) {
        return this.gameContainer.world().build().terrainVoxel(color);
    }

    private JmeCursor getCursor(CursorToken cursorToken) {
        return this.gameContainer.gui().build().cursor(cursorToken);
    }
}
