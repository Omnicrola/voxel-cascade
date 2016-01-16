package com.omnicrola.voxel.engine.states;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.engine.input.GameInputAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;


/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState {


    private class ReloadListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                loadLevel(new LevelData());
            }
        }
    }

    private class MouseLookListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            gameContainer.input().setMouseGrabbed(isPressed);
        }
    }

    private class SpawnCube implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                Geometry cube = gameContainer.world().build().cube(0.25f, ColorRGBA.Red);
                Vector3f position = gameContainer.world().getCameraPosition();
                cube.setLocalTranslation(position);
                RigidBodyControl rigidBodyControl = new RigidBodyControl(1);
                cube.addControl(rigidBodyControl);
                gameContainer.physics().addControl(rigidBodyControl);

                gameContainer.world().attach(cube);
            }
        }
    }

    private IGameContainer gameContainer;

    public ActivePlayState() {
        super("Active Play");
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        addStateInput(GameInputAction.RELOAD_LEVEL, new ReloadListener());
        addStateInput(GameInputAction.MOUSE_LOOK, new MouseLookListener());
        addStateInput(GameInputAction.MOUSE_SELECT, new SpawnCube());
    }

    public void loadLevel(LevelData levelData) {
        this.stateRootNode.detachAllChildren();
        Node terrain = VoxelTerrainGenerator.load(levelData, this.gameContainer);
        this.stateRootNode.attachChild(terrain);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.input().setCameraMoveSpeed(10);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
