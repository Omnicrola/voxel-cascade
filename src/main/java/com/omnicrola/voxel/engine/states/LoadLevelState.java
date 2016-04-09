package com.omnicrola.voxel.engine.states;

import com.jme3.app.state.AbstractAppState;
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
        System.out.println("Loading ... " + percentComplete);
        if (this.asyncLevelLoader.isFinished()) {
            LevelData levelData = this.asyncLevelLoader.getLevelData();
        }
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
}
