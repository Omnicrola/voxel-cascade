package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.data.level.LevelPlayInitializer;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.ui.CursorProviderBuilder;
import com.omnicrola.voxel.ui.IUiManager;

/**
 * Created by omnic on 2/28/2016.
 */
public class ActivePlayInputStateInitializer implements IStateInitializer {
    private CursorProviderBuilder cursorProviderBuilder;

    public ActivePlayInputStateInitializer(CursorProviderBuilder cursorProviderBuilder) {
        this.cursorProviderBuilder = cursorProviderBuilder;
    }

    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        IWorldCursor worldCursor = initializationContainer.getWorldManager().getWorldCursor();
        IUiManager uiManager = initializationContainer.getUiManager();

        return new ActivePlayState(worldCursor, uiManager, new LevelPlayInitializer(initializationContainer, cursorProviderBuilder));
    }
}
