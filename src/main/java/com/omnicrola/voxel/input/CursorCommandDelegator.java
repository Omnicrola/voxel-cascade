package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.input.actions.AttackCursorStrategy;
import com.omnicrola.voxel.input.actions.BuildUnitStrategy;
import com.omnicrola.voxel.input.actions.MoveSelectedUnitsStrategy;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
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
        JmeCursor defaultCursor = this.gameContainer.gui().build().cursor(CursorToken.DEFAULT);
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = new SelectUnitsCursorStrategy(this.gameContainer,
                this, levelState, worldCursor, defaultCursor);
        this.worldCursor.setCursorStrategy(selectUnitsCursorStrategy);
        return selectUnitsCursorStrategy;
    }

    public void setAttackStrategy() {
        JmeCursor attackCursor = this.gameContainer.gui().build().cursor(CursorToken.ATTACK);
        AttackCursorStrategy attackCursorStrategy = new AttackCursorStrategy(this.levelState, attackCursor);
        this.worldCursor.setCursorStrategy(attackCursorStrategy);
    }

    public void setMoveStrategy() {
        JmeCursor moveCursor = this.gameContainer.gui().build().cursor(CursorToken.MOVE);
        MoveSelectedUnitsStrategy moveSelectedUnitsStrategy = new MoveSelectedUnitsStrategy(this.levelState, this.worldCursor, moveCursor);
        this.worldCursor.setCursorStrategy(moveSelectedUnitsStrategy);
    }

    public void setBuildStrategy(SelectionGroup selectionGroup) {
        JmeCursor buildCursor = this.gameContainer.gui().build().cursor(CursorToken.BUILD);
        EmptyBuildStrategy emptyBuildStrategy = new EmptyBuildStrategy(buildCursor);
        this.worldCursor.setCursorStrategy(emptyBuildStrategy);
    }

    public void setBuildUnitStrategy(int unitId) {
        JmeCursor buildCursor = this.gameContainer.gui().build().cursor(CursorToken.BUILD);
        Spatial exampleBuildTarget = this.gameContainer.world().build().unit(unitId, TeamData.NEUTRAL);
        Material material  = this.gameContainer.world().build().material(MaterialToken.GHOST_BUILDING);
        exampleBuildTarget.setMaterial(material);
        BuildUnitStrategy buildUnitStrategy = new BuildUnitStrategy(this.gameContainer, this.levelState, unitId, buildCursor, exampleBuildTarget);
        this.worldCursor.setCursorStrategy(buildUnitStrategy);
    }
}
