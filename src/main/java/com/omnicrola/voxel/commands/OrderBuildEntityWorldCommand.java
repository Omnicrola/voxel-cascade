package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by Eric on 3/19/2016.
 */
@Serializable
public class OrderBuildEntityWorldCommand extends AbstractWorldCommand {

    private UnitPlacement unitPlacement;

    public OrderBuildEntityWorldCommand() {
    }

    public OrderBuildEntityWorldCommand(UnitPlacement unitPlacement) {
        this.unitPlacement = unitPlacement;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldEntityBuilder entityBuilder = commandPackage.getEntityBuilder();
        WorldManager worldManager = commandPackage.getWorldManager();
        Unit newUnit = entityBuilder.buildUnit(unitPlacement);
        worldManager.addUnit(newUnit);
    }
}
