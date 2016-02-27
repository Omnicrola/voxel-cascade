package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.AbortMultiplayerConnectionCommand;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerScreenController extends AbstractScreenController {


    private UiAdapter uiAdapter;

    public MultiplayerScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "BUTTON_ABORT_SERVER_CONNECT")
    @SubscriberLink(UiToken.BUTTON_ABORT_SERVER_CONNECT)
    public void quitGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new AbortMultiplayerConnectionCommand());
    }

    @NiftyEventSubscriber(id = "BUTTON_START_MULTIPLAYER")
    @SubscriberLink(UiToken.BUTTON_START_MULTIPLAYER)
    public void startGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new StartMultiplayerGameCommand(LevelGeneratorTool.BASIC_LEVEL_UUID));
    }

}
