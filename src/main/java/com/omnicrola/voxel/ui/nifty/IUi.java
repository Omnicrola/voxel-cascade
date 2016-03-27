package com.omnicrola.voxel.ui.nifty;

import com.omnicrola.voxel.ui.UiToken;
import de.lessvoid.nifty.controls.DropDown;

/**
 * Created by Eric on 1/30/2016.
 */
public interface IUi {
    IUiElement getElement(UiToken token);

    IUiButton getButton(UiToken token);

    <T> DropDown<T> getDropdown(UiToken token);
}
