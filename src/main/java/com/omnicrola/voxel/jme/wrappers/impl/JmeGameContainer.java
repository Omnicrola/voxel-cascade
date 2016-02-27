package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.app.state.AppState;
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
public class JmeGameContainer implements IGameContainer {
    private final VoxelGameEngine game;
    private final JmeGuiWrapper guiWrapper;
    private final JmeWorldWrapper worldWrapper;
//    private final JmeInputWrapper inputWrapper;
    private final JmePhysicsWrapper physicsWrapper;
    private final JmeNetworkWrapper networkWrapper;

    public JmeGameContainer(VoxelGameEngine game) {
        this.game = game;
        this.guiWrapper = new JmeGuiWrapper(game);
        this.physicsWrapper = new JmePhysicsWrapper(game.getPhysicsSpace());
        this.worldWrapper = new JmeWorldWrapper(game, this, this.physicsWrapper);
//        this.inputWrapper = new JmeInputWrapper(game);
        this.networkWrapper = new JmeNetworkWrapper(game);
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
        return null;
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
    public void addState(AppState gameState) {
        this.game.getStateManager().attach(gameState);
    }

    @Override
    public <T extends AppState> T getState(Class<T> stateClass) {
        return this.game.getStateManager().getState(stateClass);
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

    @Override
    public IGameNetwork network() {
        return this.networkWrapper;
    }

    @Override
    public void quitAndExit() {
        this.game.stop();
    }
}
