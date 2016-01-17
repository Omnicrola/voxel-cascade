package com.omnicrola.voxel.data.level;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.entities.EntityDefinition;
import com.omnicrola.voxel.entities.EntityData;
import com.omnicrola.voxel.entities.control.EntityAiController;
import com.omnicrola.voxel.entities.control.MotionGovernorControl;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.VoxelGlobals;

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
        Geometry geometry = gameContainer.world().build().sphere(0.25f, definition.getColor());
        geometry.setLocalTranslation(definition.getPlacement());

        RigidBodyControl rigidBodyControl = new RigidBodyControl(0.25f);
        geometry.addControl(rigidBodyControl);
        gameContainer.physics().addControl(rigidBodyControl);

        MotionGovernorControl motionGovernor = new MotionGovernorControl();
        EntityAiController entityAi = new EntityAiController();
        EntityData entityData = EntityData.entity(motionGovernor, entityAi);

        geometry.addControl(motionGovernor);
        geometry.addControl(entityAi);
        geometry.setUserData(VoxelGlobals.ENTITY_DATA, entityData);

        return geometry;
    }
}
