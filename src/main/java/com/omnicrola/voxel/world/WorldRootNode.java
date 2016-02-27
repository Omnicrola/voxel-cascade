package com.omnicrola.voxel.world;

import com.jme3.scene.Node;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 2/25/2016.
 */
public class WorldRootNode extends Node implements IWorldNode {

    private final Node unitsNode;
    private final Node projectilesNode;
    private final Node fxNode;
    private final Node terrainNode;

    public WorldRootNode() {
        super(GameConstants.NODE_WORLD_ROOT);
        this.unitsNode = new Node(GameConstants.NODE_UNITS);
        this.projectilesNode = new Node(GameConstants.NODE_PROJECTILES);
        this.fxNode = new Node(GameConstants.NODE_FX);
        this.terrainNode = new Node(GameConstants.NODE_TERRAIN);
        this.attachChild(unitsNode);
        this.attachChild(projectilesNode);
        this.attachChild(fxNode);
        this.attachChild(terrainNode);
    }

    @Override
    public Node getUnitsNode() {
        return this.unitsNode;
    }

    @Override
    public Node getProjectilesNode() {
        return this.projectilesNode;
    }

    @Override
    public Node getFxNode() {
        return this.fxNode;
    }

    @Override
    public Node getTerrainNode() {
        return this.terrainNode;
    }
}
