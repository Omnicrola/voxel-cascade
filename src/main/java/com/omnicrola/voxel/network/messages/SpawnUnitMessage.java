package com.omnicrola.voxel.network.messages;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Unit;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/25/2016.
 */
@Serializable
public class SpawnUnitMessage extends AbstractWorldMessage {
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
        worldManager.addUnit(gameUnit);


//        TeamData teamData = levelState.getTeamById(unitPlacement.getTeamId());
//        Spatial entity = this.gameContainer.world().build().unit(unitPlacement.getUnitId(), teamData);
//        Vector3f position = terrainControl.findLowestNonSolidVoxel(unitPlacement.getLocation());
//        entity.getControl(GroundVehicleControl.class).setPhysicsLocation(position);
//        entity.setLocalTranslation(position);
//        levelState.addEntity(entity);
    }
}
