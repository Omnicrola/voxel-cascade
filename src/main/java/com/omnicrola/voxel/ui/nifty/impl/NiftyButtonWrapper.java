package com.omnicrola.voxel.ui.nifty.impl;

import com.omnicrola.voxel.ui.nifty.IUiButton;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.ButtonControl;
import de.lessvoid.nifty.screen.Screen;

/**
 * Created by Eric on 1/30/2016.
 */
public class NiftyButtonWrapper implements IUiButton {
    private final Nifty nifty;
    private final Screen screen;
    private final ButtonControl button;

    public NiftyButtonWrapper(Nifty nifty, Screen screen, ButtonControl button) {
        this.nifty = nifty;
        this.screen = screen;
        this.button = button;
    }

    @Override
    public void setText(String text) {
        this.button.setText(text);
    }
}
