package com.omnicrola.voxel.data.level;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.entities.EntityDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelEntityGenerator {
    public static Node create(LevelData levelData, IGameContainer gameContainer) {
        Node entitiesRootNode = new Node();

        for (EntityDefinition definition : levelData.getEntityDefinitions()) {
            entitiesRootNode.attachChild(createEntity(definition, gameContainer));
        }

        return entitiesRootNode;
    }

    public static Spatial createEntity(EntityDefinition definition, IGameContainer gameContainer) {
        return gameContainer.world().build().entity(definition);
//        Geometry geometry = gameContainer.world().build().sphere(0.25f, definition.getColor());
//        geometry.setLocalTranslation(definition.getPlacement());
//
//        RigidBodyControl rigidBodyControl = new RigidBodyControl(0.25f);
//        geometry.addControl(rigidBodyControl);
//        gameContainer.physics().addControl(rigidBodyControl);
//
//        MotionGovernorControl motionGovernor = new MotionGovernorControl();
//        WeaponsController weaponsController = new WeaponsController(gameContainer);
//        EntityAiController entityAi = new EntityAiController(motionGovernor, weaponsController);
//
//        CollisionController collisionController = new CollisionController(new EntityCollisionHandler(geometry, gameContainer.physics()));
//
//        geometry.addControl(entityAi);
//        geometry.addControl(motionGovernor);
//        geometry.addControl(weaponsController);
//        geometry.addControl(collisionController);
//        geometry.setUserData(EntityDataKeys.IS_SELECTABLE, true);
//        return geometry;
    }
}
