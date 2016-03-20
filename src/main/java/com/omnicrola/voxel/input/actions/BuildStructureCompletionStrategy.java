package com.omnicrola.voxel.input.actions;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.commands.OrderBuildStructureWorldCommand;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.data.level.UnitPlacement;

/**
 * Created by omnic on 3/12/2016.
 */
public class BuildStructureCompletionStrategy implements IBuildCompletionStrategy {
    private final int globalId;
    private final ICommandProcessor commandProcessor;

    public BuildStructureCompletionStrategy(int globalId, ICommandProcessor commandProcessor) {
        this.globalId = globalId;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public void build(Vector3f location) {
        // TODO : make team id be the current player
        int teamId = 1;
        location.setX((int) location.x);
        location.setZ((int) location.z);
        UnitPlacement unitPlacement = new UnitPlacement(this.globalId, teamId, location);
        OrderBuildStructureWorldCommand buildUnitEntityCommand = new OrderBuildStructureWorldCommand(unitPlacement);
        this.commandProcessor.addCommand(buildUnitEntityCommand);
    }

    @Override
    public boolean isAbleToBuild() {
        return true;
    }
}
