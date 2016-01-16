package com.omnicrola.voxel.engine.states;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.data.LevelData;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.data.level.LevelStateGenerator;
import com.omnicrola.voxel.engine.input.GameInputAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;


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
            currentLevelState.getWorldCursor().setVisible(!isPressed);
            gameContainer.input().setMouseGrabbed(isPressed);
        }
    }

    private class SpawnTestCube implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                Geometry cube = gameContainer.world().build().cube(0.25f, ColorRGBA.Red);
                cube.setLocalTranslation(currentLevelState.getWorldCursor().getWorldTranslation());
                RigidBodyControl rigidBodyControl = new RigidBodyControl(1);
                cube.addControl(rigidBodyControl);
                gameContainer.physics().addControl(rigidBodyControl);
                stateRootNode.attachChild(cube);
            }
        }
    }

    private IGameContainer gameContainer;
    private LevelState currentLevelState;

    public ActivePlayState() {
        super("Active Play");
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        addStateInput(GameInputAction.RELOAD_LEVEL, new ReloadListener());
        addStateInput(GameInputAction.MOUSE_LOOK, new MouseLookListener());
        addStateInput(GameInputAction.MOUSE_SELECT, new SpawnTestCube());
    }

    public void loadLevel(LevelData levelData) {
        this.stateRootNode.detachAllChildren();
        this.currentLevelState = LevelStateGenerator.create(levelData, this.gameContainer);
        this.stateRootNode.attachChild(this.currentLevelState.getTerrain());
        this.stateRootNode.attachChild(this.currentLevelState.getWorldCursor());
        this.stateRootNode.addLight(this.currentLevelState.getSun());
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.input().setCameraMoveSpeed(10);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
