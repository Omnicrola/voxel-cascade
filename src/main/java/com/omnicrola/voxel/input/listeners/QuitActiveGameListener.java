package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.engine.states.ActivePlayInputState;
import com.omnicrola.voxel.engine.states.GameOverState;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by omnic on 2/6/2016.
 */
public class QuitActiveGameListener implements ActionListener {
    private IGameContainer gameContainer;

    public QuitActiveGameListener(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            this.gameContainer.disableState(GameOverState.class);
            this.gameContainer.disableState(ActivePlayInputState.class);
            this.gameContainer.enableState(MainMenuState.class);
        }
    }
}
