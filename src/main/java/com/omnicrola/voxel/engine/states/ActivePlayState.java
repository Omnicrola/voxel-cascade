package com.omnicrola.voxel.engine.states;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;
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

        createVoxelGrid();
    }

    private void createVoxelGrid() {
        IGeometryBuilder geometryBuilder = this.gameContainer.world().build();
        for (int x = -10; x < 10; x++) {
            for (int z = -10; z < 10; z++) {
                Geometry cube = geometryBuilder.cube(0.49f, ColorRGBA.randomColor());
                cube.setLocalTranslation(x, -5, z);
                stateRoot.attachChild(cube);
            }
        }
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        this.gameContainer.gui().attach(this.guiRoot);
        this.gameContainer.world().attach(this.stateRoot);
        this.gameContainer.gui().setMouseGrabbed(true);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        gameContainer.gui().remove(this.guiRoot);
        this.gameContainer.world().remove(this.stateRoot);
    }
}
