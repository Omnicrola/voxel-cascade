package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.ChangeDisplaySettingsCommand;
import com.omnicrola.voxel.commands.ChangeScreenCommand;
import com.omnicrola.voxel.settings.DisplayModePackage;
import com.omnicrola.voxel.settings.DisplayResolution;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.DropDown;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by omnic on 4/2/2016.
 */
public class GameSettingsScreenController extends AbstractScreenController {

    private static final Logger LOGGER = Logger.getLogger(GameSettingsScreenController.class.getName());

    private UiAdapter uiAdapter;

    public GameSettingsScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "button-save")
    public void saveSettings(String id, ButtonClickedEvent buttonClickedEvent) {
        DisplayResolution displayResolution = getSelectedDisplayMode();
        boolean isFullscreen = getFullScreen();
        int antiAliasing = getSelectedAntiAliasing();

        DisplayModePackage displayModePackage = new DisplayModePackage(displayResolution, antiAliasing, isFullscreen);
        this.uiAdapter.sendCommand(new ChangeDisplaySettingsCommand(this.uiAdapter.getDisplaySettingsHandler(), displayModePackage));
    }

    private boolean getFullScreen() {
        CheckBox fullscreenCheckbox = ui().getCheckbox(UiToken.Settings.CHECKBOX_FULLSCREEN);
        return fullscreenCheckbox.isChecked();
    }

    private int getSelectedAntiAliasing() {
        DropDown<String> aliasingDropdown = ui().getDropdown(UiToken.Settings.DROPDOWN_ANTI_ALIAS);
        String selected = aliasingDropdown.getSelection();
        return this.uiAdapter.getDisplaySettingsHandler().getAliasingForIndex(selected);
    }

    private DisplayResolution getSelectedDisplayMode() {
        DropDown<DisplayResolution> displayModes = ui().getDropdown(UiToken.Settings.DROPDOWN_RESOLUTIONS);
        return displayModes.getSelection();
    }

    @NiftyEventSubscriber(id = "button-cancel")
    public void cancel(String id, ButtonClickedEvent buttonClickedEvent) {
        uiAdapter.sendCommand(new ChangeScreenCommand(UiScreen.MAIN_MENU));
    }

    @Override
    protected void screenOpen() {
        populateDisplayResolutions();
        populateSamplingModes();
        setFullscreenCheckbox();
    }

    private void setFullscreenCheckbox() {
        CheckBox fullscreenCheckbox = ui().getCheckbox(UiToken.Settings.CHECKBOX_FULLSCREEN);
        boolean isFullscreen = this.uiAdapter.getDisplaySettingsHandler().isCurrentlyFullscreen();
        fullscreenCheckbox.setChecked(isFullscreen);
    }

    private void populateSamplingModes() {
        DropDown<String> dropdown = ui().getDropdown(UiToken.Settings.DROPDOWN_ANTI_ALIAS);
        dropdown.removeAllItems(dropdown.getItems());
        this.uiAdapter.getDisplaySettingsHandler()
                .getAliasingOptions()
                .keySet()
                .stream()
                .forEach(key -> dropdown.addItem(key));
    }

    private void populateDisplayResolutions() {
        DropDown<DisplayResolution> resolutions = ui().getDropdown(UiToken.Settings.DROPDOWN_RESOLUTIONS);

        resolutions.removeAllItems(resolutions.getItems());
        List<DisplayResolution> availableResolutions = this.uiAdapter.getDisplaySettingsHandler().getAvailableResolutions();
        availableResolutions.forEach(dm -> resolutions.addItem(dm));
        DisplayResolution displayResolution = this.uiAdapter.getDisplaySettingsHandler().getCurrentResolution();
        resolutions.selectItem(displayResolution);
    }

    @Override
    protected void screenClose() {

    }


}
