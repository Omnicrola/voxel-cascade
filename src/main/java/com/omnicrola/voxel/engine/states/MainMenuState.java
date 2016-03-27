package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 1/15/2016.
 */
public class MainMenuState extends AbstractAppState {
    private Nifty niftyGui;

    public MainMenuState(Nifty niftyGui) {
        this.niftyGui = niftyGui;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        setEnabled(false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.niftyGui.gotoScreen(UiScreen.MAIN_MENU.toString());
        }
    }
}
