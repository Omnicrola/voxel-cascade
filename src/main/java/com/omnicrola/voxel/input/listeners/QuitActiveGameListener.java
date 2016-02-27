package com.omnicrola.voxel.input.listeners;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.engine.states.ActivePlayInputState;
import com.omnicrola.voxel.engine.states.GameOverState;
import com.omnicrola.voxel.engine.states.MainMenuState;

/**
 * Created by omnic on 2/6/2016.
 */
public class QuitActiveGameListener implements ActionListener {

    private AppStateManager stateManager;

    public QuitActiveGameListener(AppStateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            disableState(GameOverState.class);
            disableState(ActivePlayInputState.class);
            enableState(MainMenuState.class);
        }
    }

    private void disableState(Class<? extends AppState> appState) {
        this.stateManager.getState(appState).setEnabled(false);
    }

    private void enableState(Class<? extends AppState> appState) {
        this.stateManager.getState(appState).setEnabled(true);
    }
}
