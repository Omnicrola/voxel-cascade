package com.omnicrola.voxel.world;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManager {

    private IWorldNode worldNode;

    public WorldManager(IWorldNode worldNode) {
        this.worldNode = worldNode;
    }

    public void addEntity(IGameEntity gameEntity) {
        this.worldNode.getUnitsNode().attachChild(gameEntity.getSpatial());
    }

    public void addEffect(IGameEntity gameEntity) {
        this.worldNode.getFxNode().attachChild(gameEntity.getSpatial());
    }
}
