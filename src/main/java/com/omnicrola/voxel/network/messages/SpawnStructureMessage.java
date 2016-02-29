package com.omnicrola.voxel.network.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.commands.AbstractWorldCommand;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/25/2016.
 */
@Serializable
public class SpawnStructureMessage extends AbstractWorldCommand {
    private UnitPlacement unitPlacement;

    public SpawnStructureMessage() {
    }

    public SpawnStructureMessage(UnitPlacement unitPlacement) {
        this.unitPlacement = unitPlacement;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldEntityBuilder entityBuilder = commandPackage.getEntityBuilder();
        WorldManager worldManager = commandPackage.getWorldManager();
        ITerrainManager terrainManager = commandPackage.getTerrainManager();

        Structure structure = entityBuilder.buildStructure(this.unitPlacement);
        worldManager.addStructure(structure);

        Vector3f position = terrainManager.getLowestNonSolidVoxel(this.unitPlacement.getLocation());
        structure.setLocation(position);
        worldManager.addStructure(structure);
    }
}
