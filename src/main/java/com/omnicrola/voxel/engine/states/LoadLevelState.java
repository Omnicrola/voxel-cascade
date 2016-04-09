package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.load.AsyncLevelLoader;

import java.util.UUID;

/**
 * Created by Eric on 4/8/2016.
 */
public class LoadLevelState extends AbstractAppState {

    private boolean loadIsPending;
    private boolean loadNeedsStarted;
    private UUID levelToLoad;
    private AsyncLevelLoader asyncLevelLoader;
    private AppStateManager stateManager;

    public LoadLevelState(AsyncLevelLoader asyncLevelLoader) {
        this.asyncLevelLoader = asyncLevelLoader;
        this.setEnabled(false);
    }

    @Override
    public void update(float tpf) {
        if (loadIsPending) {
            if (loadNeedsStarted) {
                startLoading();
            } else {
                updateLoadStatus();
            }
        }
    }


    private void startLoading() {
        this.loadNeedsStarted = false;
        this.asyncLevelLoader.setLevel(this.levelToLoad);
        this.asyncLevelLoader.startLoading();
    }

    private void updateLoadStatus() {
        float percentComplete = this.asyncLevelLoader.updateLoadStatus();
        if (this.asyncLevelLoader.isFinished()) {
            transitionToActivePlay();
        }
    }

    private void transitionToActivePlay() {
        LevelData levelData = this.asyncLevelLoader.getLevelData();
        this.setEnabled(false);
        ActivePlayState activePlayState = this.stateManager.getState(ActivePlayState.class);
        activePlayState.setLevelData(levelData);
        activePlayState.setEnabled(true);

        this.stateManager.getState(ShadowState.class).setEnabled(true);
        this.stateManager.getState(GameOverState.class).setEnabled(false);
    }

    public void setLevelToLoad(UUID levelToLoad) {
        this.levelToLoad = levelToLoad;
        this.loadIsPending = true;
        this.loadNeedsStarted = true;
    }

    private void enable() {

    }

    private void disable() {
        this.asyncLevelLoader.stop();
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

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
    }
}
