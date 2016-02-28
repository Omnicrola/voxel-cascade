package com.omnicrola.voxel.network.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.commands.AbstractWorldCommand;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/25/2016.
 */
@Serializable
public class SpawnUnitMessage extends AbstractWorldCommand {
    private UnitPlacement unitPlacement;

    public SpawnUnitMessage() {
    }

    public SpawnUnitMessage(UnitPlacement unitPlacement) {
        this.unitPlacement = unitPlacement;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldEntityBuilder entityBuilder = commandPackage.getEntityBuilder();
        WorldManager worldManager = commandPackage.getWorldManager();
        Unit gameUnit = entityBuilder.buildUnit(this.unitPlacement);
        ITerrainManager terrainManager = commandPackage.getTerrainManager();
        Vector3f position = terrainManager.getLowestNonSolidVoxel(this.unitPlacement.getLocation());
        gameUnit.setLocation(position);
        worldManager.addUnit(gameUnit);
    }
}
