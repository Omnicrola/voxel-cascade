package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.ChangeScreenCommand;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by omnic on 4/2/2016.
 */
public class GameSettingsScreenController extends AbstractScreenController {

    private UiAdapter uiAdapter;

    public GameSettingsScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "panel-graphics")
    public void showDisplaySettings(String id, ButtonClickedEvent buttonClickedEvent) {
        hidePanel(UiToken.Settings.PANEL_AUDIO);
        showPanel(UiToken.Settings.PANEL_GRAPHICS);
    }

    @NiftyEventSubscriber(id = "panel-audio")
    public void showAudioSettings(String id, ButtonClickedEvent buttonClickedEvent) {
        hidePanel(UiToken.Settings.PANEL_GRAPHICS);
        showPanel(UiToken.Settings.PANEL_AUDIO);
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
        hidePanel(UiToken.Settings.PANEL_AUDIO);
        showPanel(UiToken.Settings.PANEL_GRAPHICS);
    }

    @Override
    protected void screenClose() {

    }

    private void hidePanel(String token) {
//        ui().getElement(token).setVisible(false);
    }

    private void showPanel(String token) {
//        ui().getElement(token).setVisible(true);
    }
}
