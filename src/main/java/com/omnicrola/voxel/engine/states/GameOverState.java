package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/6/2016.
 */
public class GameOverState extends AbstractAppState {

    private Nifty niftyGui;

    public GameOverState(Nifty niftyGui) {
        this.niftyGui = niftyGui;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.setEnabled(false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.niftyGui.gotoScreen(UiScreen.GAME_OVER.toString());
        }
    }
}
