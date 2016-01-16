package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.omnicrola.voxel.engine.states.LoadingState;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.engine.states.PlayState;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngineInitializer {
    public static void initializeGame(AppStateManager stateManager) {
        BulletAppState bulletAppState = new BulletAppState();
        LoadingState loadingState = new LoadingState();
        MainMenuState mainMenuState = new MainMenuState();
        PlayState playState = new PlayState();

        stateManager.attach(loadingState);
        stateManager.attach(bulletAppState);
        stateManager.attach(mainMenuState);
        stateManager.attach(playState);
    }
}
