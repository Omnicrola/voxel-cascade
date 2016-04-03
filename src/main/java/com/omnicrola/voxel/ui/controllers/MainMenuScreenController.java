package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.BrowseForMultiplayerGameCommand;
import com.omnicrola.voxel.commands.CreateMultiplayerGameCommand;
import com.omnicrola.voxel.commands.ShutdownAndExitCommand;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
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

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_START")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_START)
    public void createMultiplayerGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new CreateMultiplayerGameCommand());
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_JOIN")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_JOIN)
    public void findMultiplayerGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new BrowseForMultiplayerGameCommand());
    }

    @NiftyEventSubscriber(id = "BUTTON_QUIT_GAME")
    @SubscriberLink(UiToken.BUTTON_QUIT_GAME)
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
