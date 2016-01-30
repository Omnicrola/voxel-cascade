package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/30/2016.
 */
public interface ICommand {

    void execute(IGameContainer gameContainer, LevelState currentLevel, SelectionGroup selectionGroup);

    String getIcon();
}
