package com.omnicrola.voxel.engine.states.transitions;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.states.*;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class TransitionMainMenu implements IStateTransition {
    @Override
    public void run(Nifty niftyGui, AppStateManager stateManager) {
        stateManager.getState(MainMenuState.class).setEnabled(true);

        stateManager.getState(GameOverState.class).setEnabled(false);
        stateManager.getState(ActivePlayState.class).setEnabled(false);
        stateManager.getState(VoxelTerrainState.class).setEnabled(false);
        stateManager.getState(ShadowState.class).setEnabled(false);

        niftyGui.gotoScreen(UiScreen.MAIN_MENU.toString());

    }
}
