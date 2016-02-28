package com.omnicrola.voxel.network.messages;

import com.jme3.network.serializing.Serializable;
import com.omnicrola.voxel.data.level.UnitPlacement;
import com.omnicrola.voxel.entities.Structure;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/25/2016.
 */
@Serializable
public class SpawnStructureMessage extends AbstractWorldMessage {
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
        Structure structure = entityBuilder.buildStructure(this.unitPlacement);
        worldManager.addStructure(structure);

//        TeamData teamData = levelState.getTeamById(placement.getTeamId());
//
//        Spatial structure = this.gameContainer.world().build().structure(placement.getUnitId(), teamData);
//        Vector3f position = terrainControl.findLowestNonSolidVoxel(placement.getLocation());
//        structure.getControl(RigidBodyControl.class).setPhysicsLocation(position);
//        levelState.addEntity(structure);

    }
}
