package com.omnicrola.voxel.entities.commands;

import com.jme3.cursors.plugins.JmeCursor;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.MoveSelectedUnitsStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.CursorToken;

/**
 * Created by omnic on 1/30/2016.
 */
public class MoveCommand implements ICommand {


    @Override
    public void execute(IGameContainer gameContainer, LevelState currentLevel, SelectionGroup selectionGroup) {
        WorldCursor worldCursor = currentLevel.getWorldCursor();
        JmeCursor moveCursor = gameContainer.gui().build().cursor(CursorToken.MOVE);
        MoveSelectedUnitsStrategy moveSelectedUnitsStrategy = new MoveSelectedUnitsStrategy(currentLevel, worldCursor, moveCursor);
        worldCursor.setCursorStrategy(moveSelectedUnitsStrategy);
    }

    @Override
    public String getIcon() {
        return "Interface/buttons/button-move.png";
    }
}
