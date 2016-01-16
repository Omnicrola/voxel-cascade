package com.omnicrola.voxel.engine.states;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.omnicrola.voxel.engine.input.GameAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.GLabel;
import org.lwjgl.input.Keyboard;

/**
 * Created by omnic on 1/15/2016.
 */
public class MainMenuState extends VoxelGameState {
    private class StartGameListener implements ActionListener {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            gameContainer.enableState(ActivePlayState.class);
            gameContainer.disableState(MainMenuState.class);
        }
    }

    private IGameContainer gameContainer;
    private Node stateRoot;
    private StartGameListener startGameListener;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.startGameListener = new StartGameListener();
        this.stateRoot = new Node();
        GLabel titleText = this.gameContainer.gui().build().label("Main Menu", ColorRGBA.Green);
        titleText.setTextPosition(300, 300);
        stateRoot.attachChild(titleText);
        setEnabled(false);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        this.gameContainer.gui().attach(this.stateRoot);
        this.gameContainer.input().bind(GameAction.SELECT, this.startGameListener);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        gameContainer.gui().remove(this.stateRoot);
        this.gameContainer.input().unbind(this.startGameListener);
    }
}
