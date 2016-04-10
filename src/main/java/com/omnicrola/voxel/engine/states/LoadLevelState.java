package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.LevelSettings;
import com.omnicrola.voxel.data.level.load.AsyncLevelLoader;

/**
 * Created by Eric on 4/8/2016.
 */
public class LoadLevelState extends AbstractAppState {

    private boolean loadIsPending;
    private boolean loadNeedsStarted;
    private LevelSettings levelToLoad;
    private ILevelManager levelManager;
    private AsyncLevelLoader asyncLevelLoader;
    private AppStateManager stateManager;

    public LoadLevelState(ILevelManager levelManager, AsyncLevelLoader asyncLevelLoader) {
        this.levelManager = levelManager;
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

    public void setLevelToLoad(LevelSettings levelToLoad) {
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

    public ILevelManager getLevelManager() {
        return levelManager;
    }
}
