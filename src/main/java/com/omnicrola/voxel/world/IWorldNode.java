package com.omnicrola.voxel.world;

import com.jme3.scene.Node;

/**
 * Created by Eric on 2/25/2016.
 */
public interface IWorldNode {
    Node getUnitsNode();

    Node getProjectilesNode();

    Node getFxNode();
}
