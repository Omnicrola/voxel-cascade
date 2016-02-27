package com.omnicrola.voxel.entities.control.old;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.EntityCollisionHandler;
import com.omnicrola.voxel.fx.VoxelFireSpawnAction;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/17/2016.
 */

@XmlRootElement(name = "CollisionControl")
public class CollisionControlFactory implements IControlFactory {



    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository) {
        IGameWorld gameWorld = gameContainer.world();
        EntityCollisionHandler entityCollisionHandler = new EntityCollisionHandler(spatial, gameWorld);
        entityCollisionHandler.setDeathAction(new VoxelFireSpawnAction(gameWorld.build(), 30));
        CollisionController collisionController = new CollisionController(entityCollisionHandler);
        spatial.addControl(collisionController);
    }
}
