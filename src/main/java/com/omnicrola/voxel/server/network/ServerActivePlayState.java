package com.omnicrola.voxel.server.network;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.server.main.ActiveMultiplayerGame;
import com.omnicrola.voxel.server.main.VoxelServerEngine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerActivePlayState extends AbstractAppState {

    private VoxelServerEngine game;
    private ActiveMultiplayerGame currentGame;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.game = (VoxelServerEngine) app;
        this.setEnabled(false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    private void disable() {

    }

    private void enable() {

    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        Logger.getLogger(ServerActivePlayState.class.getName()).log(Level.INFO, "Closing server play state");
        if (this.currentGame != null) {
            this.currentGame.cleanup();
        }
    }

    public void setCurrentGame(ActiveMultiplayerGame currentGame) {
        this.currentGame = currentGame;

    }
}
