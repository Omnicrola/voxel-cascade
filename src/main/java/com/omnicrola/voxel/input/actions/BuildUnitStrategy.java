package com.omnicrola.voxel.input.actions;

import com.jme3.cursors.plugins.JmeCursor;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.ICursorStrategy;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;

/**
 * Created by Eric on 1/30/2016.
 */
public class BuildUnitStrategy implements ICursorStrategy {
    private final LevelState levelState;
    private WorldCursor worldCursor;
    private final SelectionGroup selectionGroup;
    private final JmeCursor buildCursor;

    public BuildUnitStrategy(LevelState levelState, WorldCursor worldCursor, SelectionGroup selectionGroup, JmeCursor buildCursor) {
        this.levelState = levelState;
        this.worldCursor = worldCursor;
        this.selectionGroup = selectionGroup;
        this.buildCursor = buildCursor;
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {

    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {
        this.worldCursor.clearCursorStrategy();
    }

    @Override
    public JmeCursor get2DCursor() {
        return this.buildCursor;
    }
}
