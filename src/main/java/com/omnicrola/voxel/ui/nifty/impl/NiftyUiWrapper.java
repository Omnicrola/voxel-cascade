package com.omnicrola.voxel.ui.nifty.impl;

import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.ui.nifty.IUi;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.nifty.IUiButton;
import com.omnicrola.voxel.ui.nifty.IUiElement;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.ButtonControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

/**
 * Created by Eric on 1/30/2016.
 */
public class NiftyUiWrapper implements IUi {
    private final Nifty nifty;
    private final Screen screen;

    public NiftyUiWrapper(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public IUiElement getElement(UiToken token) {
        Element selectionPanel = this.screen.findElementByName(token.toString());
        if (selectionPanel == null) {
            throw new VoxelException("UI element not found: " + token.toString());
        }
        return new NiftyElementWrapper(this.nifty, this.screen, selectionPanel);
    }

    @Override
    public IUiButton getButton(UiToken token) {
        ButtonControl button = this.screen.findControl(token.toString(), ButtonControl.class);
        return new NiftyButtonWrapper(this.nifty, this.screen, button);
    }
}
