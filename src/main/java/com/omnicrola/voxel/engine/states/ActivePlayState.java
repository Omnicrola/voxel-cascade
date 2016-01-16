package com.omnicrola.voxel.engine.states;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState {


    private IGameContainer gameContainer;
    private Node guiRoot;
    private Node stateRoot;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.guiRoot = new Node();
        this.stateRoot = new Node();
        GLabel label = gameContainer.gui().build().label("You are playing", ColorRGBA.Cyan);
        label.setTextPosition(300, 300);
        guiRoot.attachChild(label);
    }

    public void loadLevel(LevelData levelData){
        this.stateRoot.detachAllChildren();
        Node terrain = VoxelTerrainGenerator.load(levelData, this.gameContainer);
        this.stateRoot.attachChild(terrain);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        this.gameContainer.gui().attach(this.guiRoot);
        this.gameContainer.getWorld().attach(this.stateRoot);
        this.gameContainer.gui().setMouseGrabbed(true);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        gameContainer.gui().remove(this.guiRoot);
        this.gameContainer.getWorld().remove(this.stateRoot);
    }
}
