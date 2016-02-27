package com.omnicrola.voxel.ui;

import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class UiManager implements IUiManager {
    private Nifty niftyGui;

    public UiManager(Nifty niftyGui) {
        this.niftyGui = niftyGui;
    }

    @Override
    public void changeScreen(UiScreen uiScreen) {
        this.niftyGui.gotoScreen(uiScreen.toString());
    }
}
