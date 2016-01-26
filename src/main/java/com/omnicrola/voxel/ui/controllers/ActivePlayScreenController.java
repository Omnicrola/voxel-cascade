package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 1/25/2016.
 */
public class ActivePlayScreenController extends AbstractScreenController {
    public ActivePlayScreenController() {
    }

    @NiftyEventSubscriber(id = "ACTION_1")
    @SubscriberLink(UiToken.ACTION_1)
    public void triggerActionButton_1(String id, ButtonClickedEvent buttonClickedEvent) {
        System.out.println("action 1");
    }

    @NiftyEventSubscriber(id = "ACTION_2")
    @SubscriberLink(UiToken.ACTION_2)
    public void triggerActionButton_2(String id, ButtonClickedEvent buttonClickedEvent) {
        System.out.println("action 2");
    }

    @NiftyEventSubscriber(id = "ACTION_3")
    @SubscriberLink(UiToken.ACTION_3)
    public void triggerActionButton_3(String id, ButtonClickedEvent buttonClickedEvent) {
        System.out.println("action 3");
    }
}
