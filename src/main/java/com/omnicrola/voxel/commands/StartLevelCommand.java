package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 2/28/2016.
 */
@Serializable
public class StartLevelCommand extends AbstractWorldCommand {
    @Override
    public void execute(CommandPackage commandPackage) {
        LevelState currentLevel = commandPackage.getLevelManager().getCurrentLevel();
        currentLevel.setHasStarted(true);
    }
}
