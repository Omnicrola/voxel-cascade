package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerScreenController extends AbstractScreenController{


    private IGameContainer gameContainer;

    public MultiplayerScreenController(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }


    @NiftyEventSubscriber(id = "BUTTON_ABORT_SERVER_CONNECT")
    @SubscriberLink(UiToken.BUTTON_ABORT_SERVER_CONNECT)
    public void quitGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.gameContainer.network().closeConnection();
        this.gameContainer.gui().changeScreens(UiScreen.MAIN_MENU);
    }

}
