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

    private IGameWorld gameWorld;

    public CollisionControlFactory(IGameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void build(Spatial spatial) {
        CollisionController collisionController = new CollisionController(new EntityCollisionHandler(spatial, gameWorld));
        spatial.addControl(collisionController);
    }
}
