package com.omnicrola.voxel.network.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.commands.AbstractWorldCommand;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

import java.util.Optional;

/**
 * Created by Eric on 2/25/2016.
 */
@Serializable
public class SpawnUnitCommand extends AbstractWorldCommand {
    private UnitPlacement unitPlacement;

    public SpawnUnitCommand() {
    }

    public SpawnUnitCommand(UnitPlacement unitPlacement) {
        this.unitPlacement = unitPlacement;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        WorldEntityBuilder entityBuilder = commandPackage.getEntityBuilder();
        WorldManager worldManager = commandPackage.getWorldManager();
        Unit gameUnit = entityBuilder.buildUnit(this.unitPlacement);
        ITerrainManager terrainManager = commandPackage.getTerrainManager();

        Optional<VoxelData> voxel = terrainManager.findLowestEmptyVoxel(this.unitPlacement.getLocation());
        if (voxel.isPresent()) {
            Vector3f location = voxel.get().getLocation();
            gameUnit.setLocation(location);
            worldManager.addUnit(gameUnit);
        }
    }
}
