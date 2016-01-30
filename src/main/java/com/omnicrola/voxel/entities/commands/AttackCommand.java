package com.omnicrola.voxel.entities.commands;

import com.jme3.cursors.plugins.JmeCursor;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.input.actions.AttackCursorStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.CursorToken;

/**
 * Created by omnic on 1/30/2016.
 */
public class AttackCommand implements ICommand {

    @Override
    public void execute(IGameContainer gameContainer, LevelState currentLevel, SelectionGroup selectionGroup) {
        JmeCursor attackCursor = gameContainer.gui().build().cursor(CursorToken.ATTACK);
        AttackCursorStrategy attackCursorStrategy = new AttackCursorStrategy(currentLevel, attackCursor);
        currentLevel.getWorldCursor().setCursorStrategy(attackCursorStrategy);
    }

    @Override
    public String getIcon() {
        return "Interface/buttons/button-attack.png";
    }
}
