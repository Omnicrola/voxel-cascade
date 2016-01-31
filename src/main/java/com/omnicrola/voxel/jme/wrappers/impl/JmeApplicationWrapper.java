package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.VoxelGameState;
import com.omnicrola.voxel.jme.wrappers.*;

import java.util.List;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeApplicationWrapper implements IGameContainer {
    private final VoxelGameEngine game;
    private final JmeGuiWrapper guiWrapper;
    private final JmeWorldWrapper worldWrapper;
    private final JmeInputWrapper inputWrapper;
    private final JmePhysicsWrapper physicsWrapper;

    public JmeApplicationWrapper(VoxelGameEngine game) {
        this.game = game;
        this.guiWrapper = new JmeGuiWrapper(game);
        this.physicsWrapper = new JmePhysicsWrapper(game);
        this.worldWrapper = new JmeWorldWrapper(game, this, this.physicsWrapper);
        this.inputWrapper = new JmeInputWrapper(game);
    }

    @Override
    public IGameGui gui() {
        return this.guiWrapper;
    }

    @Override
    public IGameWorld world() {
        return this.worldWrapper;
    }

    @Override
    public IGameInput input() {
        return this.inputWrapper;
    }

    @Override
    public IGamePhysics physics() {
        return this.physicsWrapper;
    }

    @Override
    public void enableState(Class<? extends VoxelGameState> stateClass) {
        this.game.getStateManager().getState(stateClass).setEnabled(true);
    }

    @Override
    public void disableState(Class<? extends VoxelGameState> stateClass) {
        this.game.getStateManager().getState(stateClass).setEnabled(false);
    }

    @Override
    public AssetManager getAssetManager() {
        return this.game.getAssetManager();
    }

    public void debugSceneGraph() {
        Node rootNode = this.game.getRootNode();
        recursiveTreePrint(rootNode, "");
    }

    private void recursiveTreePrint(Spatial spatial, String prefix) {
        System.out.println(prefix + spatial.getName() + " : " + spatial.getWorldTranslation());
        if (spatial instanceof Node) {
            List<Spatial> children = ((Node) spatial).getChildren();
            children.forEach(c -> recursiveTreePrint(c, prefix + "- "));
        }
    }
}
