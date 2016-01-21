package com.omnicrola.voxel.data.level;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelEntityGenerator {
    public static Node create(LevelDefinition levelData, IGameContainer gameContainer) {
        Node entitiesRootNode = new Node();

        for (UnitPlacement unitPlacement : levelData.getUnitPlacements()) {
            Spatial entity = gameContainer.world().build().unit(unitPlacement.getUnitId());
            entity.setLocalTranslation(unitPlacement.getLocation());
            entitiesRootNode.attachChild(entity);
        }

        return entitiesRootNode;
    }
}
