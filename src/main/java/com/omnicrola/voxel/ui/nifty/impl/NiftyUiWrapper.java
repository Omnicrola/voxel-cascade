package com.omnicrola.voxel.ui.nifty.impl;

import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.ui.nifty.IUi;
import com.omnicrola.voxel.ui.nifty.IUiButton;
import com.omnicrola.voxel.ui.nifty.IUiElement;
import com.omnicrola.voxel.ui.nifty.IUiPanel;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.ListBox;
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
    public IUiElement getElement(String token) {
        Element selectionPanel = this.screen.findElementByName(token);
        if (selectionPanel == null) {
            throw new VoxelException("UI element not found: " + token);
        }
        return new NiftyElementWrapper(this.nifty, this.screen, selectionPanel);
    }

    @Override
    public IUiButton getButton(String token) {
        ButtonControl button = this.screen.findControl(token, ButtonControl.class);
        return new NiftyButtonWrapper(this.nifty, this.screen, button);
    }

    @Override
    public <T> DropDown<T> getDropdown(String token) {
        return this.screen.findNiftyControl(token, DropDown.class);
    }

    @Override
    public <T> ListBox<T> getListBox(String token) {
        return this.screen.findNiftyControl(token, ListBox.class);
    }

    @Override
    public CheckBox getCheckbox(String token) {
        return this.screen.findNiftyControl(token, CheckBox.class);
    }

    @Override
    public IUiPanel getPanel(String token) {
        return getElement(token);
    }
}
