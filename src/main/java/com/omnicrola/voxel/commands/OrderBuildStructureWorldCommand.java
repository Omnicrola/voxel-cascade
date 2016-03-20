package com.omnicrola.voxel.commands;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by Eric on 3/19/2016.
 */
@Serializable
public class OrderBuildStructureWorldCommand extends AbstractWorldCommand {
    private UnitPlacement unitPlacement;

    public OrderBuildStructureWorldCommand() {
    }

    public OrderBuildStructureWorldCommand(UnitPlacement unitPlacement) {
        this.unitPlacement = unitPlacement;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldEntityBuilder entityBuilder = commandPackage.getEntityBuilder();
        WorldManager worldManager = commandPackage.getWorldManager();
        Structure newStructure = entityBuilder.buildStructure(unitPlacement);
        worldManager.addStructure(newStructure);
    }
}
