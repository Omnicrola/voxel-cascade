package com.omnicrola.voxel.entities.control.collision;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.fx.VoxelFireSpawnAction;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;
import com.omnicrola.voxel.world.WorldManager;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/17/2016.
 */

@XmlRootElement(name = "CollisionControl")
public class CollisionControlFactory implements IControlFactory {



    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository, EntityControlAdapter entityControlAdapter) {
        WorldManager worldManager = entityControlAdapter.getWorldManager();
        ParticleBuilder effectsBuilder = entityControlAdapter.getParticleBuilder();

        EntityCollisionHandler entityCollisionHandler = new EntityCollisionHandler(spatial, worldManager);
        entityCollisionHandler.setDeathAction(new VoxelFireSpawnAction(effectsBuilder, 30));

        CollisionController collisionController = new CollisionController(entityCollisionHandler);
        spatial.addControl(collisionController);
    }
}
