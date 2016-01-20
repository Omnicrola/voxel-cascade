package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.EntityCollisionHandler;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/17/2016.
 */

@XmlRootElement(name = "CollisionControl")
public class CollisionControlFactory implements IControlFactory {
    @Override
    public void build(Spatial spatial, IGameWorld gameWorld) {
        CollisionController collisionController = new CollisionController(new EntityCollisionHandler(spatial, gameWorld));
        spatial.addControl(collisionController);
    }
}
