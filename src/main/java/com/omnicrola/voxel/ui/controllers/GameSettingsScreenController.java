package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.ChangeScreenCommand;
import com.omnicrola.voxel.settings.DisplaySettingsHandler;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.DropDown;
import org.lwjgl.opengl.DisplayMode;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by omnic on 4/2/2016.
 */
public class GameSettingsScreenController extends AbstractScreenController {

    private static final Logger LOGGER = Logger.getLogger(GameSettingsScreenController.class.getName());

    private UiAdapter uiAdapter;
    private DisplaySettingsHandler displaySettingsHandler;

    public GameSettingsScreenController(UiAdapter uiAdapter, DisplaySettingsHandler displaySettingsHandler) {
        this.uiAdapter = uiAdapter;
        this.displaySettingsHandler = displaySettingsHandler;
    }

    @NiftyEventSubscriber(id = "panel-graphics")
    public void showDisplaySettings(String id, ButtonClickedEvent buttonClickedEvent) {
    }

    @NiftyEventSubscriber(id = "panel-audio")
    public void showAudioSettings(String id, ButtonClickedEvent buttonClickedEvent) {
    }

    @NiftyEventSubscriber(id = "button-save")
    public void saveSettings(String id, ButtonClickedEvent buttonClickedEvent) {
        System.out.println("Save!");
    }

    @NiftyEventSubscriber(id = "button-cancel")
    public void cancel(String id, ButtonClickedEvent buttonClickedEvent) {
        uiAdapter.sendCommand(new ChangeScreenCommand(UiScreen.MAIN_MENU));
    }

    @Override
    protected void screenOpen() {
        populateDisplayResolutions();
        populateSamplingModes();
    }

    private void populateSamplingModes() {
        DropDown<String> dropdown = ui().getDropdown(UiToken.Settings.DROPDOWN_ANTI_ALIAS);
        dropdown.removeAllItems(dropdown.getItems());
        dropdown.addItem("None");
        dropdown.addItem("4x");
        dropdown.addItem("8x");
    }

    private void populateDisplayResolutions() {
        DropDown<DisplayMode> resolutions = ui().getDropdown(UiToken.Settings.DROPDOWN_RESOLUTIONS);

        resolutions.removeAllItems(resolutions.getItems());
        List<DisplayMode> availableDisplayModes = this.displaySettingsHandler.getAvailableDisplayModes();
        availableDisplayModes.forEach(dm -> resolutions.addItem(dm));
        DisplayMode currentMode = this.displaySettingsHandler.getCurrentDisplayMode();
        resolutions.selectItem(currentMode);
    }


    @Override
    protected void screenClose() {

    }

}
