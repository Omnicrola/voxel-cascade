package com.omnicrola.voxel.input.actions;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.commands.BuildEntityWorldCommand;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.data.level.UnitPlacement;

/**
 * Created by omnic on 3/12/2016.
 */
public class BuildUnitCompletionStrategy implements IBuildCompletionStrategy {
    private int unitId;
    private ICommandProcessor commandProcessor;

    public BuildUnitCompletionStrategy(int unitId, ICommandProcessor commandProcessor) {
        this.unitId = unitId;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public void build(Vector3f location) {
        // TODO : make team id be the current player
        int teamId = 1;
        UnitPlacement unitPlacement = new UnitPlacement(this.unitId, teamId, location.add(0, 5, 0));
        BuildEntityWorldCommand buildUnitEntityCommand = new BuildEntityWorldCommand(unitPlacement);
        this.commandProcessor.addCommand(buildUnitEntityCommand);
    }

    @Override
    public boolean isAbleToBuild() {
        // TODO : add cost
        return true;
    }
}
