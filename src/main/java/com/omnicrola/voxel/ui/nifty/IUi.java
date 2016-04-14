package com.omnicrola.voxel.ui.nifty;

import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.ListBox;

/**
 * Created by Eric on 1/30/2016.
 */
public interface IUi {
    IUiElement getElement(String token);

    IUiButton getButton(String token);

    <T> DropDown<T> getDropdown(String token);

    <T> ListBox<T> getListBox(String token);

    CheckBox getCheckbox(String checkboxFullscreen);

    IUiPanel getPanel(String token);

    IUiChatbox getChatbox(String token);
}
