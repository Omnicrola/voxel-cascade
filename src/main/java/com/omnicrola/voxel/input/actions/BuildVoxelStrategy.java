package com.omnicrola.voxel.input.actions;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/31/2016.
 */
public class BuildVoxelStrategy implements ICursorStrategy {
    private final IGameContainer gameContainer;
    private final LevelState levelState;
    private final byte voxelType;
    private final JmeCursor buildCursor;
    private final Node cursor3d;

    public BuildVoxelStrategy(IGameContainer gameContainer, LevelState levelState, byte voxelType, JmeCursor buildCursor, Geometry ghostVoxel) {
        this.gameContainer = gameContainer;
        this.levelState = levelState;
        this.voxelType = voxelType;
        this.buildCursor = buildCursor;
        this.cursor3d = new Node();
        this.cursor3d.attachChild(ghostVoxel);
    }

    @Override
    public void executePrimary(GameMouseEvent gameMouseEvent, SelectionGroup currentSelection) {
        Vec3i snappedLocation = this.levelState.getWorldCursor().getSnappedLocation();
        this.levelState.createVoxel(this.voxelType, snappedLocation);
        if (!gameMouseEvent.isMultiSelecting()) {
            this.levelState.getWorldCursor().clearCursorStrategy();
        }
    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        this.levelState.getWorldCursor().clearCursorStrategy();
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.buildCursor;
    }

    @Override
    public Spatial get3dCursor() {
        return this.cursor3d;
    }
}
