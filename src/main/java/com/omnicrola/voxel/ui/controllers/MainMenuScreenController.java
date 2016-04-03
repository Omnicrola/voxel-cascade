package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.BrowseForMultiplayerGameCommand;
import com.omnicrola.voxel.commands.CreateMultiplayerGameCommand;
import com.omnicrola.voxel.commands.ShutdownAndExitCommand;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 1/24/2016.
 */
public class MainMenuScreenController extends AbstractScreenController {

    private UiAdapter uiAdapter;

    public MainMenuScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "button-new-game")
    public void createMultiplayerGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new CreateMultiplayerGameCommand());
    }

    @NiftyEventSubscriber(id = "button-browse-games")
    public void findMultiplayerGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new BrowseForMultiplayerGameCommand());
    }

    @NiftyEventSubscriber(id = "button-quit-game")
    public void quitGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new ShutdownAndExitCommand());
    }

    @Override
    protected void screenOpen() {

    }

    @Override
    protected void screenClose() {

    }
}
