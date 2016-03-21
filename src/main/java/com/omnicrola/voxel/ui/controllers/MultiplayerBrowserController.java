package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerBrowserController extends AbstractScreenController {
    private UiAdapter uiAdapter;

    public MultiplayerBrowserController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_BROWSE_JOIN")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_BROWSE_JOIN)
    public void triggerJoin(String id, ButtonClickedEvent buttonClickedEvent) {

    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_BROWSE_CANCEL")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_BROWSE_CANCEL)
    public void triggerCancel(String id, ButtonClickedEvent buttonClickedEvent) {

    }

}
